package com.example.di

import com.example.repository.AuthorRepository
import com.example.repository.AuthorRepositoryImpl
import com.example.repository.BookRepository
import com.example.repository.BookRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single {
        Database.connect(
            "jdbc:postgresql://localhost:5432/books", "org.postgresql.Driver",
            user = "postgres", password = "123"
        )
    }
    single<BookRepository> { BookRepositoryImpl(get()) }
    single<AuthorRepository> { AuthorRepositoryImpl(get()) }
}
