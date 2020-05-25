package com.vitorblog.json.model

import com.vitorblog.json.Json

class JsonValue {
    val value:Any

    constructor(value:Any) {

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

}