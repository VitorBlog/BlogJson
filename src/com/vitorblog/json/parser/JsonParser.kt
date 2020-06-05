package com.vitorblog.json.parser

import com.vitorblog.json.Json
import com.vitorblog.json.model.exception.InvalidJsonException

class JsonParser(text: String, debug: Boolean = false) {

    var json: Json = Json()
    var status = Status.NOTHING

    var key: String = ""
    var value: String = ""

    init {
        if (!text.startsWith("{") || !text.endsWith("}")) {
            throw InvalidJsonException("curly brackets are invalid")
        }

        val characters = text.toCharArray()
        for (char in characters) {

            when (Token.byValue(char.toString(), status)) {

                Token.TEXT_QUOTES -> {

                    when (status) {

                        /* Json */
                        Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                        /* Key */
                        Status.NOTHING -> {
                            status = Status.READING_KEY
                        }

                        Status.READING_KEY -> {
                            status = Status.WAITING_VALUE
                        }

                        /* String */
                        Status.WAITING_VALUE -> {
                            status = Status.READING_STRING
                        }

                        Status.READING_STRING -> {
                            status = Status.NOTHING

                            setValue()
                        }

                        /* Int */
                        Status.READING_INT -> {
                            status = Status.READING_KEY

                            setValue(value.toInt())
                        }

                        /* Boolean */
                        Status.READING_BOOLEAN -> {
                            status = Status.READING_KEY

                            setValue(value.subSequence(0, value.length - 1) == "true")
                        }

                    }

                }

                Token.INT -> {

                    when (status) {

                        /* Json */
                        Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                        Status.WAITING_VALUE -> {
                            status = Status.READING_INT
                            value += char
                        }

                        Status.READING_INT -> {
                            value += char
                        }

                    }

                }

                Token.BOOLEAN -> {

                    when (status) {

                        /* Json */
                        Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                        Status.WAITING_VALUE -> {
                            status = Status.READING_BOOLEAN
                            value += char
                        }

                        Status.READING_BOOLEAN -> {
                            value += char
                        }

                    }

                }

                Token.CURLY_OPEN -> {

                    when (status) {

                        Status.WAITING_VALUE -> {
                            status = Status.READING_JSON
                            value += char
                        }

                        Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                    }

                }

                Token.CURLY_CLOSE -> {

                    when (status) {

                        Status.READING_JSON -> {
                            status = Status.NOTHING
                            value += char
                            setValue(JsonParser(value, false).toJson()!!)
                        }

                        Status.READING_ARRAY -> {
                            value += char
                        }

                        Status.READING_STRING -> {
                            status = Status.NOTHING

                            setValue()
                        }

                        /* Int */
                        Status.READING_INT -> {
                            status = Status.READING_KEY

                            setValue(value.toInt())
                        }

                        /* Boolean */
                        Status.READING_BOOLEAN -> {
                            status = Status.READING_KEY

                            setValue(value.subSequence(0, value.length - 1) == "true")
                        }

                    }

                }

                Token.SQUARE_OPEN -> {

                    when (status) {

                        Status.WAITING_VALUE -> {
                            status = Status.READING_ARRAY
                            value += char
                        }

                        Status.READING_ARRAY -> {
                            value += char
                        }

                    }

                }

                Token.SQUARE_CLOSE -> {

                    when (status) {

                        Status.READING_ARRAY -> {
                            status = Status.NOTHING
                            value += char
                            setValue(JsonArrayParser(value.substring(1), false).toArray()!!)
                        }

                        Status.READING_STRING -> {
                            status = Status.NOTHING

                            setValue()
                        }

                        /* Int */
                        Status.READING_INT -> {
                            status = Status.READING_KEY

                            setValue(value.toInt())
                        }

                        /* Boolean */
                        Status.READING_BOOLEAN -> {
                            status = Status.READING_KEY

                            setValue(value.subSequence(0, value.length - 1) == "true")
                        }

                    }

                }

                Token.NULL, null -> {

                    when (status) {

                        Status.READING_KEY -> {
                            key += char
                        }

                        Status.READING_STRING, Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                        Status.READING_INT -> {
                            status = Status.NOTHING

                            setValue(value.toInt())
                        }

                        Status.READING_BOOLEAN -> {
                            status = Status.NOTHING

                            setValue(value == "true")
                        }

                    }

                }

            }

            if (debug)
                println(
                    "[STATUS = ${status.name} | TOKEN = ${Token.byValue(
                        char.toString(),
                        status
                    )?.name} | CHAR = $char] \"$key\": $value"
                )

        }

    }

    fun setValue(any: Any = value) {
        json.set(key, any)

        key = ""
        value = ""
    }

    fun toJson(): Json? = json

}