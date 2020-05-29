package com.vitorblog.json.parser

enum class Status {

    NOTHING,
    READING_KEY,
    READING_VALUE,
    WAITING_VALUE,

}