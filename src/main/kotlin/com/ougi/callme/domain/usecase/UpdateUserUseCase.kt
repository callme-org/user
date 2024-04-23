package com.ougi.callme.domain.usecase

import com.ougi.callme.data.model.dto.UpdateUserDto
import com.ougi.callme.domain.model.UserToUpdate
import com.ougi.callme.domain.repository.UserRepository
import io.ktor.server.plugins.*

interface UpdateUserUseCase {

    suspend fun updateUser(user: UserToUpdate)
}

class UpdateUserUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateUserUseCase {

    override suspend fun updateUser(user: UserToUpdate) {
        userRepository.read(user.login)
            ?.run {
                userRepository.update(
                    UpdateUserDto(
                        id = id,
                        login = user.newLogin ?: login,
                        username = user.username ?: username,
                    )
                )
            }
            ?: throw NotFoundException()
    }

}