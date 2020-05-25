package com.vitorblog.json.model

import com.vitorblog.json.Json
import com.vitorblog.json.util.TypeUtils

object JsonFormatter {

    fun format(json: Json, tabSize:Int = 0):String {
        var newLine = "\n"
        var tabs = "    "

        if (tabSize > 0) { repeat(tabSize) { tabs += "    " } } else if (tabSize <= -1) { tabs = "";newLine = "" }

        var string = "{$newLine"

        for ((key, jsonValue) in json.fields) {

            string += "$tabs\"$key\": ${jsonValue.toString(tabSize)},$newLine"

        }

        return "${string.substring(0, string.length-2)}$newLine${tabs.substring(4)}}"
    }

    fun format(jsonArray: JsonArray, tabSize:Int = 0):String {
        var newLine = "\n"
        var tabs = "    "

        if (tabSize > 0) { repeat(tabSize) { tabs += "    " } } else if (tabSize <= -1) { tabs = "";newLine = "" }

        var string = "[$newLine"

        for (value in jsonArray) {

            val jsonValue = JsonValue(value)
            string += "$tabs${jsonValue.toString(tabSize)},$newLine"

        }

        return "${string.substring(0, string.length-2)}$newLine${tabs.substring(4)}]"
    }

}