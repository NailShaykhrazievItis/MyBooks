package com.example.repository

import com.example.model.dc.User

interface UserRepository {

    suspend fun addUser(name: String, email: String, password: String): User?

    suspend fun findUserById(id: Int): User?

    suspend fun findUserByEmail(email: String): User?

    suspend fun deleteUser(id: Int)

    suspend fun getAllUsers(): List<User>
}