package com.vitorblog.json.model.exception

class JsonValueNotFoundException(key: String) : Exception("Can't find a value with '$key' Key")