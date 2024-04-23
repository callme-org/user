package com.ougi.callme.data.repository

import com.ougi.callme.data.model.dto.CreateUserDto
import com.ougi.callme.data.model.dto.SelectUserDto
import com.ougi.callme.data.model.dto.UpdateUserDto
import com.ougi.callme.data.model.table.UserTable
import com.ougi.callme.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {

    override suspend fun create(user: CreateUserDto) = dbQuery {
        UserTable.insert { column ->
            column[login] = user.login
        }[UserTable.id]
    }

    override suspend fun read(login: String): SelectUserDto? = dbQuery {
        UserTable.selectAll()
            .where { UserTable.login eq login }
            .map { result ->
                SelectUserDto(
                    id = result[UserTable.id],
                    login = result[UserTable.login],
                    username = result[UserTable.username]
                )
            }
            .singleOrNull()
    }

    override suspend fun update(updatedUser: UpdateUserDto) = dbQuery {
        UserTable.update({ UserTable.id eq updatedUser.id }) { column ->
            updatedUser.login?.let { newLogin -> column[login] = newLogin }
            updatedUser.username?.let { newUsername -> column[username] = newUsername }
        }
    }

    private suspend fun <T> dbQuery(query: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { query() }
}