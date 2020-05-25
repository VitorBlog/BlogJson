package com.vitorblog.json

import com.vitorblog.json.model.JsonValue
import com.vitorblog.json.model.exception.JsonValueNotFoundException
import com.vitorblog.json.util.TypeUtils
import java.io.File

class Json {

    /* Value functions */

    val fields = hashMapOf<String, JsonValue>()

    operator
    fun get(key:String, smartGet:Boolean = true): JsonValue {

        return (if (key.contains(".") && smartGet) {
            smartGet(key)
        } else {
            fields[key]
        }) ?: throw JsonValueNotFoundException(key)

    }

    private
    fun smartGet(keys:String): JsonValue? {

        val split = keys.split(".")
        var jsonValue:JsonValue? = null

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

    fun getOrNull(key:String, smartGet:Boolean = true): JsonValue? {

        return try {
            get(key, smartGet)
        } catch (exception:JsonValueNotFoundException) {
            null
        }

    }

    fun set(key:String, value:Any): Json {
        fields[key] = JsonValue(value)
        return this
    }

    /* Input/Output functions */

    override
    fun toString():String {
        var string = "{"

        for ((key, jsonValue) in fields) {
            val value = when (TypeUtils.identifyType(jsonValue)) {
                TypeUtils.JsonType.INT, TypeUtils.JsonType.BOOLEAN -> "${jsonValue.value}"
                TypeUtils.JsonType.JSON -> "${jsonValue.asJson()}"
                TypeUtils.JsonType.ARRAY -> "${jsonValue.asJsonArray()}"
                else -> "\"${jsonValue.value}\""
            }

            string += "\"$key\":$value,"
        }

        return "${string.substring(0, string.length-1)}}"
    }

    fun toFile(file: File) = file.writeText(toString())

    companion object {

        fun parse(file:File) = parse(file.readText())

        fun parse(string: String): Json? {
            return null
        }

    }

}