package com.example.model.dao

import com.example.model.dc.Author
import com.example.model.entity.Authors
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class AuthorDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AuthorDao>(Authors)

    val name by Authors.name
    val birthDate by Authors.birthDate
    val deathDate by Authors.deathDate

    fun toAuthor() = Author(
        id = id.value,
        name = name,
        birthDate = birthDate.millis,
        deathDateTime = deathDate.millis
    )
}
