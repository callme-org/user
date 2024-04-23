package com.ougi.callme.domain.usecase

import com.ougi.callme.domain.model.User
import com.ougi.callme.domain.repository.UserRepository

interface GetUserUseCase {

    suspend fun getUser(login: String): User?

}

class GetUserUseCaseImpl(
    private val userRepository: UserRepository,
) : GetUserUseCase {

    override suspend fun getUser(login: String) =
        userRepository.read(login)?.let { dto ->
            User(
                login = dto.login,
                username = dto.username
            )
        }

}