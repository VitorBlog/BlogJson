package com.vitorblog.json.model

import com.vitorblog.json.Json
import com.vitorblog.json.model.exception.InvalidJsonException

class JsonParser(text:String) {

    var json:Json = Json()
    var status = Status.NOTHING

    var key:String = ""
    var value:String = ""

    init {

        if (!text.startsWith("{") || !text.endsWith("}")) {
            throw InvalidJsonException("curly/square brackets are invalid")
        }

        val characters = text.toCharArray()
        for (char in characters) {

            when (Token.byValue(char.toString())) {

                Token.TEXT_QUOTES -> {

                    when (status) {

                        Status.NOTHING -> {
                            status = Status.READING_KEY
                        }

                        Status.READING_KEY -> {
                            status = Status.WAITING_VALUE
                        }

                        Status.WAITING_VALUE -> {
                            status = Status.READING_VALUE
                        }

                        Status.READING_VALUE -> {
                            status = Status.NOTHING

                            json.set(key, value)
                            key = ""
                            value = ""
                        }

                    }

                }

                null -> {

                    when (status) {

                        Status.READING_KEY -> {
                            key += char
                        }

                        Status.READING_VALUE -> {
                            value += char
                        }

                    }

                }

            }

        }

    }

    fun toJson(): Json? = json

    enum class Status {

        NOTHING,
        READING_KEY,
        READING_VALUE,
        WAITING_VALUE,

    }

    enum class Token(var value:String) {

        CURLY_OPEN("{"),
        CURLY_CLOSE("{"),
        TEXT_QUOTES("\""),
        SQUARE_OPEN("["),
        SQUARE_CLOSE("]"),
        BOOLEAN_TRUE("true"),
        BOOLEAN_FALSE("false"),
        OPEN_VALUE(":"),
        NULL("null");

        companion object {
            fun byValue(value:String): Token? {
                return values().firstOrNull { it.value == value }
            }
        }

    }

}