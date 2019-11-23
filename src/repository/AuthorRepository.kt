package com.example.repository

import com.example.model.dc.Author
import org.jetbrains.exposed.sql.transactions.experimental.TransactionResult

interface AuthorRepository {

    suspend fun getAllBooks(): TransactionResult<List<Author>>

    suspend fun getBook(id: Int): TransactionResult<Author?>

    suspend fun addBook(book: Author)

    suspend fun deleteBook(id: Int)

    suspend fun clear()
}
