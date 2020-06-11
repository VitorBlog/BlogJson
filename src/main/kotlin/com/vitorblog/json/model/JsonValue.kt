package com.vitorblog.json.model

import com.vitorblog.json.Json
import com.vitorblog.json.util.TypeUtils

class JsonValue {
    val value: Any?

    constructor(value: Any?) {

        if (value is Collection<*>) {
            this.value = JsonArray(value)
        } else {
            this.value = value
        }

    }

    fun asString() = value as String
    fun asInt() = value as Int
    fun asBoolean() = value as Boolean
    fun asJson() = value as Json
    fun asJsonArray() = value as JsonArray
    fun asNull() = null

    fun toString(tabSize: Int = -1): String {
        return when (TypeUtils.identifyType(this)) {
            TypeUtils.JsonType.INT, TypeUtils.JsonType.BOOLEAN, TypeUtils.JsonType.NULL -> "${this.value}"
            TypeUtils.JsonType.JSON -> JsonFormatter.format(this.asJson(), tabSize + 1)
            TypeUtils.JsonType.ARRAY -> JsonFormatter.format(this.asJsonArray(), tabSize + 1)
            else -> "\"${this.value}\""
        }
    }

}