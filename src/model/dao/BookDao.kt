package com.example.model.dao

import com.example.model.dc.Book
import com.example.model.entity.Books
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class BookDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookDao>(Books)

    var name by Books.name
    var pages by Books.pages
    var date by Books.date
    var author by AuthorDao optionalReferencedOn Books.author

    fun toBook(): Book = Book(
        id = id.value,
        name = name,
        pages = pages,
        date = date.millis,
        author = author?.toAuthor()
    )
}
