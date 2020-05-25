package com.vitorblog.json.util

import com.vitorblog.json.Json
import com.vitorblog.json.model.JsonValue

object TypeUtils {

    fun identifyType(jsonValue:JsonValue):JsonType? {

        return when (jsonValue.value) {
            is String -> JsonType.STRING
            is Int -> JsonType.INT
            is Boolean -> JsonType.BOOLEAN
            is List<*> -> JsonType.ARRAY
            is Json -> JsonType.JSON
            else -> null
        }

    }

    enum class JsonType {

        STRING,
        INT,
        BOOLEAN,
        ARRAY,
        JSON;

    }

}