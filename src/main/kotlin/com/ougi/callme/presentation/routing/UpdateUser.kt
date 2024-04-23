package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.model.UserToUpdate
import com.ougi.callme.domain.usecase.UpdateUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.update() {
    val updateUserUseCase by inject<UpdateUserUseCase>()
    post("/update") {
        val userToUpdate = call.receiveNullable<UserToUpdate>()
        userToUpdate
            ?.let { user ->
                updateUserUseCase.updateUser(user)
                call.respond(
                    HttpStatusCode.OK,
                    "User successfully updated"
                )
            }
            ?: call.respond(HttpStatusCode.Conflict)
    }
}