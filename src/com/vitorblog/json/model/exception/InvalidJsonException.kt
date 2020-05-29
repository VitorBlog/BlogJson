package com.vitorblog.json.model.exception

import java.lang.Exception

class InvalidJsonException(text:String) : Exception("Can't parse your json because the $text.")