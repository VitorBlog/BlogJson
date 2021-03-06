package com.vitorblog.json

import com.vitorblog.json.model.JsonFormatter
import com.vitorblog.json.model.JsonValue
import com.vitorblog.json.model.exception.JsonValueNotFoundException
import com.vitorblog.json.parser.JsonParser
import java.io.File

class Json {

    /* Value functions */

    val fields = hashMapOf<String, JsonValue>()

    operator
    fun get(key: String, smartGet: Boolean = true): JsonValue {

        return (if (key.contains(".") && smartGet) {
            smartGet(key)
        } else {
            fields[key]
        }) ?: throw JsonValueNotFoundException(key)

    }

    private
    fun smartGet(keys: String): JsonValue? {

        val split = keys.split(".")
        var jsonValue: JsonValue? = null

        for ((index, key) in split.withIndex()) {

            if (index == split.size) {

                if (jsonValue != null)
                    jsonValue = jsonValue.asJson()[key]

            } else {

                jsonValue = if (jsonValue != null) {

                    jsonValue.asJson()[key]

                } else {

                    this[key]

                }

            }

        }

        return jsonValue

    }

    fun getOrNull(key: String, smartGet: Boolean = true): JsonValue? {

        return try {
            get(key, smartGet)
        } catch (exception: JsonValueNotFoundException) {
            null
        }

    }

    fun set(key: String, value: Any?): Json {
        fields[key] = JsonValue(value)
        return this
    }

    /* Input/Output functions */
    override
    fun toString() = toString(false)

    fun toFormattedString() = toString(true)

    fun toString(beautify: Boolean = false): String {
        return if (beautify) {
            JsonFormatter.format(this)
        } else {
            JsonFormatter.format(this, -999)
        }
    }

    fun toClass(clazz: Class<*>): Any? {
        val constructor = clazz.constructors.firstOrNull { it.parameters.isNotEmpty() }
        var instance:Any? = null

        if (constructor == null) {

            instance = clazz.newInstance()

            for ((key, value) in this.fields) {
                val field = instance!!::class.java.getDeclaredField(key)
                field.isAccessible = true
                field.set(instance, value.value)
            }

        } else {

            val parameters = arrayListOf<Any>()

            println(fields.values)

            println(parameters)


        }

        return instance
    }

    companion object {

        fun toJson(clazz: Any): Json? {
            val json = Json()

            for (field in clazz::class.java.declaredFields) {
                field.isAccessible = true
                json.set(field.name, field.get(clazz))
            }

            return json
        }

        fun parse(file: File) = parse(file.readText())

        fun parse(string: String, debug: Boolean = false): Json? {
            return JsonParser(string, debug).toJson()
        }

    }

}