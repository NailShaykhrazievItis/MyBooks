package com.example.repository

import com.example.model.dc.Book
import kotlinx.coroutines.Deferred

interface BookRepository {

    suspend fun getAllBooks(): Deferred<List<Book>>

    suspend fun getBook(id: Int): Deferred<Book?>

    suspend fun addBook(book: Book)

    suspend fun update(id: Int, book: Book)

    suspend fun deleteBook(id: Int)

    suspend fun clear()
}
