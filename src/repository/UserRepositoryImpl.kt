package com.example.repository

import com.example.model.dc.User
import com.example.model.entity.UserDB
import com.example.model.entity.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class UserRepositoryImpl(private val db: Database) : UserRepository {

    override suspend fun addUser(name: String, email: String, password: String): User? =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            UserDB.new {
                this.email = email
                this.name = name
                this.password = password
            }.toUser()
        }.await()

    override suspend fun findUserById(id: Int): User? = suspendedTransactionAsync(Dispatchers.IO, db) {
        UserDB.findById(id)?.toUser()
    }.await()

    override suspend fun findUserByEmail(email: String): User? = suspendedTransactionAsync(Dispatchers.IO, db) {
        UserDB.find { Users.email eq email }.firstOrNull()?.toUser()
    }.await()

    override suspend fun deleteUser(id: Int) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            UserDB.findById(id)?.delete()
        }
    }

    override suspend fun getAllUsers(): List<User> = suspendedTransactionAsync(Dispatchers.IO, db) {
        UserDB.all().map { it.toUser() }
    }.await()
}