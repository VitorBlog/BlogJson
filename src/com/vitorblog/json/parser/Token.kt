package com.vitorblog.json.parser

enum class Token(var value: String) {

    CURLY_OPEN("{"),
    CURLY_CLOSE("}"),
    TEXT_QUOTES("\""),
    SQUARE_OPEN("["),
    SQUARE_CLOSE("]"),
    INT(""),
    BOOLEAN(""),
    NULL("null");

    companion object {
        fun byValue(value: String, status: Status): Token? {

            return values().firstOrNull { it.value == value } ?: when {
                value.toIntOrNull() != null -> INT
                status == Status.WAITING_VALUE && (value == "t" || value == "f") -> BOOLEAN
                status == Status.READING_BOOLEAN && value != "," -> BOOLEAN
                else -> NULL
            }

        }
    }

}