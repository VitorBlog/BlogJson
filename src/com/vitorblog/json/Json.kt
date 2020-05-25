package com.vitorblog.json

import com.vitorblog.json.model.JsonValue
import com.vitorblog.json.util.TypeUtils
import java.io.File

class Json {

    val fields = hashMapOf<String, JsonValue>()

    operator
    fun get(key:String) = fields[key]

    fun set(key:String, value:Any): Json {
        fields[key] = JsonValue(value)
        return this
    }

    override
    fun toString():String {
        var string = "{"

        for ((key, jsonValue) in fields) {
            val value = when (TypeUtils.identifyType(jsonValue)) {
                TypeUtils.JsonType.INT, TypeUtils.JsonType.BOOLEAN -> "${jsonValue.value}"
                TypeUtils.JsonType.JSON -> "${jsonValue.asJson()}"
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