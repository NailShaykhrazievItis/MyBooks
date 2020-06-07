package com.example.repository

import com.example.model.dc.AuthorDTO
import com.example.model.entity.Author
import com.example.model.entity.Authors
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.joda.time.DateTime

class AuthorRepositoryImpl(private val db: Database) : AuthorRepository {

    override suspend fun getAll(): Deferred<List<AuthorDTO>> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            Author.all().map { it.toAuthor() }
        }

    override suspend fun getAuthor(id: Int): Deferred<AuthorDTO?> =
        suspendedTransactionAsync(Dispatchers.IO, db) {
            Author.findById(id)?.toAuthor()
        }

    override suspend fun addAuthor(author: AuthorDTO) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            Author.new {
                name = author.name
                birthDate = DateTime(author.birthDate)
                deathDate = DateTime(author.deathDateTime)
            }
        }
    }

    override suspend fun deleteAuthor(id: Int) {
        newSuspendedTransaction {
            Author.findById(id)?.delete()
        }
    }

    override suspend fun clear() {
        newSuspendedTransaction {
            Authors.deleteAll()
        }
    }
}
