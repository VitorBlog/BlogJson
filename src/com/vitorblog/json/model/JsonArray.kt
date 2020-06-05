package com.vitorblog.json.model

import com.vitorblog.json.Json
import com.vitorblog.json.parser.JsonArrayParser
import com.vitorblog.json.parser.JsonParser
import java.io.File

class JsonArray(collection: Collection<*>? = null) : ArrayList<Any>(collection ?: listOf()) {

    /* Input/Output functions */
    override
    fun toString() = toString(true)

    fun toFormattedString() = toString(true)

    fun toString(beautify:Boolean = false):String {
        return if (beautify) {
            JsonFormatter.format(this)
        } else {
            JsonFormatter.format(this, -999)
        }
    }

    companion object {

        fun parse(file: File) = parse(file.readText())

        fun parse(string: String, debug: Boolean = false): JsonArray? {
            return JsonArrayParser(string, debug).toArray()
        }

    }

}