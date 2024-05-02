package com.ougi.callme.domain.repository

import com.ougi.callme.data.model.dto.CreateUserDto
import com.ougi.callme.data.model.dto.SelectUserDto
import com.ougi.callme.data.model.dto.UpdateUserDto

interface UserRepository {

    suspend fun create(user: CreateUserDto)

    suspend fun read(login: String): SelectUserDto?

    suspend fun update(updatedUser: UpdateUserDto)


}