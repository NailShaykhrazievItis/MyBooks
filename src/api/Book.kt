package com.example.api

import com.example.API_VERSION
import com.example.model.dc.ErrorMessage
import com.example.repository.BookRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

const val BOOK_ENDPOINT = "$API_VERSION/book"

fun Routing.books(bookRepository: BookRepository) {

    route(BOOK_ENDPOINT) {
        get("/") {
            call.respond(mapOf("books" to bookRepository.getAllBooks().await()))
        }
        get("/{id}") {
            bookRepository.getBook(call.parameters["id"]?.toInt() ?: -1).await()?.also {
                call.respond(mapOf("book" to it))
            } ?: run {
                call.respond(HttpStatusCode.NotFound, ErrorMessage("Book not found").toJson())
            }
        }
        post("/") {
//            val requst = call.receive<>()
        }
    }
}