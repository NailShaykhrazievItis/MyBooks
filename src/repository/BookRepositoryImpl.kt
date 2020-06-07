package com.example.repository

import com.example.model.dc.BookDTO
import com.example.model.entity.Book
import com.example.model.entity.Books
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.joda.time.DateTime

class BookRepositoryImpl(private val db: Database) : BookRepository {

    override suspend fun getAllBooks() = suspendedTransactionAsync(Dispatchers.IO, db) {
        Book.all().map { it.toBook() }
    }

    override suspend fun getBook(id: Int): Deferred<BookDTO?> = suspendedTransactionAsync(Dispatchers.IO, db) {
        Book.findById(id)?.toBook()
    }

    override suspend fun addBook(book: BookDTO) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            Book.new {
                name = book.name
                pages = book.pages
                date = DateTime(book.date)
            }
        }
    }

    override suspend fun update(id: Int, book: BookDTO) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            Book.findById(id)?.apply {
                name = book.name
                pages = book.pages
                date = DateTime(book.date)
            }
        }
    }

    override suspend fun deleteBook(id: Int) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            Book.findById(id)?.delete()
        }
    }

    override suspend fun clear() {
        newSuspendedTransaction(Dispatchers.IO, db) {
            Books.deleteAll()
        }
    }
}
