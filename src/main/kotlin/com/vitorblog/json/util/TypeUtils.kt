package com.vitorblog.json.util

import com.vitorblog.json.Json
import com.vitorblog.json.model.JsonValue

object TypeUtils {

    fun identifyType(jsonValue: JsonValue): JsonType? {

        return if (jsonValue.value == null) {
            JsonType.NULL
        } else {
            when (jsonValue.value) {
                is String -> JsonType.STRING
                is Int -> JsonType.INT
                is Boolean -> JsonType.BOOLEAN
                is Collection<*> -> JsonType.ARRAY
                is Json -> JsonType.JSON
                else -> null
            }
        }

    }

    enum class JsonType {

        STRING,
        INT,
        BOOLEAN,
        ARRAY,
        JSON,
        NULL;

    }

}