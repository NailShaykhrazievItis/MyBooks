package com.example.repository

import com.example.model.dc.Author
import kotlinx.coroutines.Deferred

interface AuthorRepository {

    suspend fun getAll(): Deferred<List<Author>>

    suspend fun getAuthor(id: Int): Deferred<Author?>

    suspend fun addAuthor(author: Author)

    suspend fun deleteAuthor(id: Int)

    suspend fun clear()
}
