package com.example.model.entity

import org.jetbrains.exposed.dao.IntIdTable

object Authors : IntIdTable() {
    val name = varchar("name", 127)
    val birthDate = date("birth_date")
    val deathDate = date("death_date")
}
