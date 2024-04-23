package com.ougi.callme.domain.usecase

import com.ougi.callme.data.model.dto.CreateUserDto
import com.ougi.callme.domain.repository.UserRepository

interface CreateUserUseCase {

    suspend fun createUser(login: String)

}

class CreateUserUseCaseImpl(
    private val userRepository: UserRepository,
) : CreateUserUseCase {

    override suspend fun createUser(login: String) {
        userRepository.create(
            CreateUserDto(
                login = login
            )
        )
    }

}