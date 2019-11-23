package com.example.repository

import com.example.model.dao.AuthorDao
import com.example.model.dc.Author
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.TransactionResult
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class AuthorRepositoryImpl(private val db: Database) : AuthorRepository {

    override suspend fun getAllBooks(): TransactionResult<List<Author>> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            AuthorDao.all().map { it.toAuthor() }
        }

    override suspend fun getBook(id: Int): TransactionResult<Author?> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            AuthorDao.findById(id)?.toAuthor()
        }

    override suspend fun addBook(book: Author) {
    }

    override suspend fun deleteBook(id: Int) {
    }

    override suspend fun clear() {
    }
}
