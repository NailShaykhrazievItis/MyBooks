package com.example.di

import com.example.auth.JwtService
import com.example.repository.*
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
    single<UserRepository> { UserRepositoryImpl(get()) }
}

val serviceModule = module {
    single { JwtService() }
}
