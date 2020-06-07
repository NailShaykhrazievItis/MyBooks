package com.example.repository

import com.example.model.dc.BookDTO
import kotlinx.coroutines.Deferred

interface BookRepository {

    suspend fun getAllBooks(): Deferred<List<BookDTO>>

    suspend fun getBook(id: Int): Deferred<BookDTO?>

    suspend fun addBook(book: BookDTO)

    suspend fun update(id: Int, book: BookDTO)

    suspend fun deleteBook(id: Int)

    suspend fun clear()
}
