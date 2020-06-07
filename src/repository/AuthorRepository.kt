package com.example.repository

import com.example.model.dc.AuthorDTO
import kotlinx.coroutines.Deferred

interface AuthorRepository {

    suspend fun getAll(): Deferred<List<AuthorDTO>>

    suspend fun getAuthor(id: Int): Deferred<AuthorDTO?>

    suspend fun addAuthor(author: AuthorDTO)

    suspend fun deleteAuthor(id: Int)

    suspend fun clear()
}
