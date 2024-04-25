package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.model.UserLogin
import com.ougi.callme.domain.usecase.CreateUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject
import java.sql.SQLIntegrityConstraintViolationException

fun Route.create() {
    val createUserUseCase by inject<CreateUserUseCase>()
    post("/create") {
        val userLogin = call.receiveNullable<UserLogin>()
        userLogin?.login?.let { login ->
            runCatching {
                createUserUseCase.createUser(login)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "User successfully created"
                )
            }.onFailure { th ->
                if (th is ExposedSQLException && th.cause is SQLIntegrityConstraintViolationException)
                    call.respond(
                        status = HttpStatusCode.Conflict,
                        message = "User already created"
                    )
                else
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = th.message ?: th.localizedMessage
                    )
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}