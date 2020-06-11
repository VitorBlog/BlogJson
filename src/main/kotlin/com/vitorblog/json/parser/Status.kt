package com.vitorblog.json.parser

enum class Status {

    NOTHING,
    READING_KEY,
    READING_STRING,
    READING_INT,
    READING_BOOLEAN,
    READING_JSON,
    READING_ARRAY,
    WAITING_VALUE;

}