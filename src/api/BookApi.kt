package com.example.api

import com.example.API_VERSION
import com.example.model.dc.Book
import com.example.model.dc.ErrorMessage
import com.example.repository.BookRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

const val BOOK_ENDPOINT = "$API_VERSION/books"

fun Routing.books(bookRepository: BookRepository) {

    route(BOOK_ENDPOINT) {
        get("/") {
            call.respond(mapOf("books" to bookRepository.getAllBooks().await()))
        }
        authenticate("jwt") {
            get("/{id}") {
                bookRepository.getBook(call.parameters["id"]?.toIntOrNull() ?: -1).await()?.also {
                    call.respond(mapOf("book" to it))
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, ErrorMessage("Book not found").toJson())
                }
            }
        }
        post("/") {
            val request = call.receive<Book>()
            bookRepository.addBook(request)
            call.respond(HttpStatusCode.Created)
        }
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: -1
            val body = call.receive<Book>()
            bookRepository.update(id, body)
            call.respond(HttpStatusCode.NotFound, ErrorMessage("There is no record with id: $id").toJson())
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: -1
            bookRepository.deleteBook(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
