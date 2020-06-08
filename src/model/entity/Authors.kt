package com.example.model.entity

import com.example.model.dc.Author
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.date

object Authors : IntIdTable() {
    val name = varchar("name", 127)
    val birthDate = date("birth_date")
    val deathDate = date("death_date")
}

class AuthorDB(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AuthorDB>(Authors)

    var name by Authors.name
    var birthDate by Authors.birthDate
    var deathDate by Authors.deathDate

    fun toAuthor() = Author(
        id = id.value,
        name = name,
        birthDate = birthDate.millis,
        deathDateTime = deathDate.millis
    )
}
