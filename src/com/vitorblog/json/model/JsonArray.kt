package com.vitorblog.json.model

import com.vitorblog.json.util.TypeUtils
import java.io.File

class JsonArray : ArrayList<Any> {

    constructor(collection:Collection<*>? = null) : super(collection ?: listOf())

    /* Input/Output functions */
    override
    fun toString() = toString(true)

    fun toFormattedString() = toString(true)

    fun toString(beautify:Boolean = false):String {
        return if (beautify) {
            JsonFormatter.format(this)
        } else {
            JsonFormatter.format(this, -1)
        }
    }

}