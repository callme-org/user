package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.model.UserLogin
import com.ougi.callme.domain.usecase.CreateUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.create() {
    val createUserUseCase by inject<CreateUserUseCase>()
    post("/create") {
        val userLogin = call.receiveNullable<UserLogin>()
        userLogin?.login?.let { login ->
            createUserUseCase.createUser(login)
            call.respond(
                HttpStatusCode.OK,
                "User successfully created"
            )
        } ?: call.respond(HttpStatusCode.Conflict)
    }
}