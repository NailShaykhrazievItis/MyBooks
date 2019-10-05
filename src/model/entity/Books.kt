package com.example.model.entity

import org.jetbrains.exposed.dao.IntIdTable

object Books : IntIdTable() {
    val name = varchar("name", 127)
    val pages = integer("page_count")
    val date = date("date")
    val author = reference("author_id", Authors).nullable()
}
