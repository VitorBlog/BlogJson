package com.vitorblog.json.model

import com.vitorblog.json.Json

object JsonFormatter {

    fun format(json: Json, tabSize: Int = 0): String {
        var newLine = "\n"
        var tabs = "    "

        if (tabSize > 0) {
            repeat(tabSize) { tabs += "    " }
        } else if (tabSize <= -1) {
            tabs = ""; newLine = ""
        }

        var string = "$newLine"

        var index = 0
        for ((key, jsonValue) in json.fields) {

            string += "$tabs\"$key\": ${jsonValue.toString(tabSize)}" +
                    if (json.fields.size - 1 == index) {
                        newLine
                    } else {
                        ",$newLine"
                    }
            index++

        }

        return if (tabSize <= -1) {
            "{${string}$tabs}"
        } else {
            "{${string.substring(0, string.length - 1)}$newLine${tabs.substring(4)}}"
        }
    }

    fun format(jsonArray: JsonArray, tabSize: Int = 0): String {
        var newLine = "\n"
        var tabs = "    "
        if (tabSize > 0) {
            repeat(tabSize) { tabs += "    " }
        } else if (tabSize <= -1) {
            tabs = ""; newLine = ""
        }

        var string = "$newLine"

        for ((index, value) in jsonArray.withIndex()) {

            val jsonValue = JsonValue(value)
            string += "$tabs${jsonValue.toString(tabSize)}" +
                    if (jsonArray.size -1 == index) {
                        newLine
                    } else {
                        ",$newLine"
                    }

        }

        return if (tabSize <= -1) {
            "[${string}$tabs]"
        } else {
            "[${string.substring(0, string.length - 1)}$newLine${tabs.substring(4)}]"
        }
    }

}