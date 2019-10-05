package com.example.repository

import com.example.model.dao.BookDao
import com.example.model.dc.Book
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.TransactionResult
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.joda.time.DateTime

class BookRepositoryImpl(private val db: Database) : BookRepository {

    override suspend fun getAllBooks() = suspendedTransactionAsync(Dispatchers.IO, db) {
        BookDao.all().map { it.toBook() }
    }

    override suspend fun getBook(id: Int): TransactionResult<Book?> = suspendedTransactionAsync(Dispatchers.IO, db) {
        BookDao.findById(id)?.toBook()
    }

    override suspend fun addBook(book: Book) {
        suspendedTransactionAsync {
            BookDao.new {
                name = book.name
                pages = book.pages
                date = DateTime(book.date)
            }
        }
    }

    override suspend fun deleteBook(id: Int) {
    }

    override suspend fun clear() {
    }
}
