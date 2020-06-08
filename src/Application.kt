package com.example

import com.example.api.books
import com.example.api.users
import com.example.auth.JwtService
import com.example.auth.MySession
import com.example.di.databaseModule
import com.example.di.serviceModule
import com.example.repository.BookRepository
import com.example.repository.UserRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.text.DateFormat

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, host = "localhost", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            setDateFormat(DateFormat.LONG)
        }
    }
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    install(Koin) {
        modules(databaseModule, serviceModule)
    }

    val userRepository: UserRepository by inject()
    val jwtService: JwtService by inject()
    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "http://localhost/"
            validate {
                it.payload.getClaim("id").asInt()?.let { id ->
                    userRepository.findUserById(id)
                }
            }
        }
    }

    val bookRepository: BookRepository by inject()

    routing {
        /**
         * A public methods
         */
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        users(userRepository, jwtService)
        // api
        books(bookRepository)
    }
}

const val API_VERSION = "/api/v1"
