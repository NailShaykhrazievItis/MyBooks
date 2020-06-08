package com.example.api

import com.example.API_VERSION
import com.example.auth.JwtService
import com.example.auth.MySession
import com.example.model.dc.errorJson
import com.example.model.dc.errorMissingFields
import com.example.repository.UserRepository
import io.ktor.application.ApplicationCall
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.sessions.sessions
import io.ktor.sessions.set

const val USERS_ENDPOINT = "$API_VERSION/users"

fun Routing.users(
    repository: UserRepository,
    jwtService: JwtService
) {
    route(USERS_ENDPOINT) {
        post("login") {
            val parameters = call.receive<Parameters>()
            val password = parameters["password"] ?: return@post call.respondMissingFields()
            val email = parameters["email"] ?: return@post call.respondMissingFields()
            try {
                repository.findUserByEmail(email)?.let {
                    if (it.password == password) {
                        call.sessions.set(MySession(it.id))
                        call.respond(mapOf("token" to jwtService.makeToken(it)))
                    } else {
                        call.respond(HttpStatusCode.BadRequest, errorJson("Problems retrieving User"))
                    }
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, errorJson("User not found"))
                }
            } catch (e: Throwable) {
                application.log.error("Failed to login user", e)
                call.respond(HttpStatusCode.BadRequest, errorJson("Problems creating User"))
            }
        }

        post("create") {
            val parameters = call.receive<Parameters>()
            val password = parameters["password"] ?: return@post call.respondMissingFields()
            val displayName = parameters["name"] ?: return@post call.respondMissingFields()
            val email = parameters["email"] ?: return@post call.respondMissingFields()

            try {
                repository.addUser(displayName, email, password)?.let {
                    call.sessions.set(MySession(it.id))
                    call.respond(HttpStatusCode.Created, mapOf("token" to jwtService.makeToken(it)))
                }
            } catch (e: Throwable) {
                application.log.error("Failed to create user", e)
                call.respond(HttpStatusCode.BadRequest, errorJson("Problems creating User"))
            }
        }

        post("logout") {
            val parameters = call.receive<Parameters>()
            val email = parameters["email"] ?: return@post call.respondMissingFields()

            try {
                repository.findUserByEmail(email)?.id?.let {
                    call.sessions.clear(call.sessions.findName(MySession::class))
                    call.respond(HttpStatusCode.OK)
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, errorJson("User not found"))
                }
            } catch (e: Throwable) {
                application.log.error("Failed to logout user", e)
                call.respond(HttpStatusCode.BadRequest, "Problems retrieving User")
            }
        }

        delete("delete") {
            val parameters = call.receive<Parameters>()
            val email = parameters["email"] ?: return@delete call.respondMissingFields()
            try {
                repository.findUserByEmail(email)?.id?.let {
                    repository.deleteUser(it)
                    call.sessions.clear(call.sessions.findName(MySession::class))
                    call.respond(HttpStatusCode.OK)
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, errorJson("User not found"))
                }
            } catch (e: Throwable) {
                application.log.error("Failed to delete user", e)
                call.respond(HttpStatusCode.BadRequest, "Problems retrieving User")
            }
        }

        get("/") {
            call.respond(mapOf("users" to repository.getAllUsers()))
        }
    }
}

suspend fun ApplicationCall.respondMissingFields() = this.respond(
    HttpStatusCode.Unauthorized, errorMissingFields()
)
