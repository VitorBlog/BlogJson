package com.vitorblog.json.parser

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

                            setValue()
                        }

                    }

                }

                Token.INT -> {

                    when (status) {

                        Status.WAITING_VALUE -> {
                            status = Status.READING_VALUE
                        }

                        Status.READING_VALUE -> {
                            value += char
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

    fun setValue(any:Any = value) {
        json.set(key, any)

        key = ""
        value = ""
    }

    fun toJson(): Json? = json

}