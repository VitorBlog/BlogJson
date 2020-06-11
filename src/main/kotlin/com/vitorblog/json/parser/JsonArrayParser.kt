package com.vitorblog.json.parser

import com.vitorblog.json.model.JsonArray

class JsonArrayParser(text: String, debug: Boolean = false) {

    var jsonArray = JsonArray()
    var status = Status.WAITING_VALUE

    var value: String = ""

    init {

        val characters = text.toCharArray()
        for (char in characters) {

            when (Token.byValue(char.toString(), status)) {

                Token.TEXT_QUOTES -> {

                    when (status) {

                        Status.READING_JSON -> {
                            value += char
                        }

                        Status.WAITING_VALUE -> {
                            status = Status.READING_STRING
                        }

                        Status.READING_STRING -> {
                            status = Status.WAITING_VALUE

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

                            setValue(value == "true")
                        }

                    }

                }

                Token.INT -> {

                    when (status) {

                        /* Json */
                        Status.READING_JSON -> {
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
                        Status.READING_JSON -> {
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

                        Status.READING_JSON -> {
                            value += char
                        }

                    }

                }

                Token.CURLY_CLOSE -> {

                    when (status) {

                        Status.READING_JSON -> {
                            status = Status.WAITING_VALUE
                            value += char
                            setValue(JsonParser(value, false).toJson()!!)
                        }

                        Status.READING_STRING -> {
                            status = Status.WAITING_VALUE

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

                            setValue(value == "true")
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

                        Status.READING_STRING -> {
                            status = Status.WAITING_VALUE

                            setValue()
                        }

                        /* Int */
                        Status.READING_INT -> {
                            status = Status.WAITING_VALUE

                            setValue(value.toInt())
                        }

                        /* Boolean */
                        Status.READING_BOOLEAN -> {
                            status = Status.WAITING_VALUE

                            setValue(value == "true")
                        }

                    }

                }

                Token.NULL, null -> {

                    when (status) {

                        Status.READING_STRING, Status.READING_JSON, Status.READING_ARRAY -> {
                            value += char
                        }

                        Status.READING_INT -> {
                            status = Status.WAITING_VALUE

                            setValue(value.toInt())
                        }

                        Status.READING_BOOLEAN -> {
                            status = Status.WAITING_VALUE

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
                    )?.name} | CHAR = $char] $value"
                )

        }
    }

    fun setValue(any: Any = value) {
        jsonArray.add(any)

        value = ""
    }

    fun toArray(): JsonArray? = jsonArray

}