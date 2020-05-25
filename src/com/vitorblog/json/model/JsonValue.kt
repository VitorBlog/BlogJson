package com.vitorblog.json.model

import com.vitorblog.json.Json

class JsonValue(val value:Any) {

    fun asString() = value as String
    fun asInt() = value as Int
    fun asBoolean() = value as Boolean
    fun asJson() = value as Json

}