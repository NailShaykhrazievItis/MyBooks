package com.example.model.entity

import com.example.model.dc.Book
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.date

object Books : IntIdTable() {
    val name = varchar("name", 127)
    val pages = integer("page_count")
    val date = date("date")
    val author = reference("author_id", Authors).nullable()
}

class BookDB(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookDB>(Books)

    var name by Books.name
    var pages by Books.pages
    var date by Books.date
    var author by AuthorDB optionalReferencedOn Books.author

    fun toBook(): Book = Book(
        id = id.value,
        name = name,
        pages = pages,
        date = date.millis,
        author = author?.toAuthor()
    )
}
