package com.example.model.dc

data class ErrorMessage(
    var message: String = "Something went wrong"
) {

    fun toJson() = mapOf("error" to message)
}
