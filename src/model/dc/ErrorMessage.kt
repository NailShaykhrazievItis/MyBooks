package com.example.model.dc

data class ErrorMessage(
    var message: String = "Something went wrong"
) {

    fun toJson() = mapOf("error" to message)
}

inline fun errorJson(message: String) = ErrorMessage(message).toJson()
inline fun errorMissingFields() = errorJson("Missing Fields")
