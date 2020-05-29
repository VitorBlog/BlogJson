package com.vitorblog.json.parser

enum class Token(var value:String) {

    CURLY_OPEN("{"),
    CURLY_CLOSE("}"),
    TEXT_QUOTES("\""),
    SQUARE_OPEN("["),
    SQUARE_CLOSE("]"),
    BOOLEAN_TRUE("true"),
    BOOLEAN_FALSE("false"),
    OPEN_VALUE(":"),
    INT(""),
    NULL("null");

    companion object {
        fun byValue(value:String): Token? {
            if (value.matches(Regex("-?\\\\d+(\\\\.\\\\d+)?"))) {
                return INT
            }

            return values().firstOrNull { it.value == value }
        }
    }

}