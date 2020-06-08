package com.example.model.dc

import io.ktor.auth.Principal

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String
) : Principal