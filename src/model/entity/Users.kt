package com.example.model.entity

import com.example.model.dc.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val email = varchar("email", 128).uniqueIndex()
    val name = varchar("name", 128)
    val passwordHash = varchar("password", 64)
}

class UserDB(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDB>(Users)

    var name by Users.name
    var email by Users.email
    var password by Users.passwordHash

    fun toUser(): User = User(
        id = id.value,
        name = name,
        email = email,
        password = password
    )
}