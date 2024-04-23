package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.model.UserLogin
import com.ougi.callme.domain.usecase.GetUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.get() {
    val getUserUseCase by inject<GetUserUseCase>()
    get("/get") {
        val userLogin = call.receiveNullable<UserLogin>()
        userLogin?.login
            ?.let { login ->
                getUserUseCase.getUser(login)
                    ?.let { user ->
                        call.respond(
                            HttpStatusCode.OK,
                            user
                        )
                    }
                    ?: call.respond(HttpStatusCode.NotFound)
            }
            ?: call.respond(HttpStatusCode.Conflict)
    }
}