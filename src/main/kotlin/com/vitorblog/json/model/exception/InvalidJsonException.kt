package com.vitorblog.json.model.exception

class InvalidJsonException(text: String) : Exception("Can't parse your json because the $text.")