package com.example.repository

import com.example.model.dc.Book
import org.jetbrains.exposed.sql.transactions.experimental.TransactionResult

interface BookRepository {

    suspend fun getAllBooks(): TransactionResult<List<Book>>

    suspend fun getBook(id: Int): TransactionResult<Book?>

    suspend fun addBook(book: Book)

    suspend fun deleteBook(id: Int)

    suspend fun clear()
}
