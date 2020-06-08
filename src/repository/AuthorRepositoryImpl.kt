package com.example.repository

import com.example.model.dc.Author
import com.example.model.entity.AuthorDB
import com.example.model.entity.Authors
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.joda.time.DateTime

class AuthorRepositoryImpl(private val db: Database) : AuthorRepository {

    override suspend fun getAll(): Deferred<List<Author>> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            AuthorDB.all().map { it.toAuthor() }
        }

    override suspend fun getAuthor(id: Int): Deferred<Author?> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            AuthorDB.findById(id)?.toAuthor()
        }

    override suspend fun addAuthor(author: Author) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            AuthorDB.new {
                name = author.name
                birthDate = DateTime(author.birthDate)
                deathDate = DateTime(author.deathDateTime)
            }
        }
    }

    override suspend fun deleteAuthor(id: Int) {
        newSuspendedTransaction {
            AuthorDB.findById(id)?.delete()
        }
    }

    override suspend fun clear() {
        newSuspendedTransaction {
            Authors.deleteAll()
        }
    }
}
