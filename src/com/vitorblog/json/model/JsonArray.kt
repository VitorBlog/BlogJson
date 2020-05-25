package com.vitorblog.json.model

import com.vitorblog.json.util.TypeUtils
import java.io.File

class JsonArray : ArrayList<Any> {

    constructor(collection:Collection<*>? = null) : super(collection ?: listOf())

    override
    fun toString():String {
        var string = "["

        for (value in this) {
            val jsonValue = JsonValue(value)
            val value = when (TypeUtils.identifyType(jsonValue)) {
                TypeUtils.JsonType.INT, TypeUtils.JsonType.BOOLEAN -> "${jsonValue.value}"
                TypeUtils.JsonType.JSON -> "${jsonValue.asJson()}"
                TypeUtils.JsonType.ARRAY -> "${jsonValue.asJsonArray()}"
                else -> "\"${jsonValue.value}\""
            }

            string += "$value,"
        }

        return "${string.substring(0, string.length-1)}]"
    }

    fun toFile(file: File) = file.writeText(toString())

}