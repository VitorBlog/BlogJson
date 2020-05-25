package com.vitorblog.json.model.exception

import java.lang.Exception

class JsonValueNotFoundException(key:String) : Exception("Can't find a value with '$key' Key")