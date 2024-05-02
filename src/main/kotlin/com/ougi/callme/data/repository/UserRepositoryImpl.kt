package com.ougi.callme.data.repository

import com.ougi.callme.data.model.dto.CreateUserDto
import com.ougi.callme.data.model.dto.SelectUserDto
import com.ougi.callme.data.model.dto.UpdateUserDto
import com.ougi.callme.data.model.table.UserTable
import com.ougi.callme.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {

    override suspend fun create(user: CreateUserDto) {
        dbQuery {
            UserTable.selectAll()
                .where { UserTable.login eq user.login }
                .singleOrNull()
                ?.let { result ->
                    update(
                        UpdateUserDto(
                            login = user.login,
                            username = result[UserTable.username],
                            newLogin = null,
                        )
                    )
                }
                ?: UserTable.insertIgnore { column ->
                    column[UserTable.login] = user.login
                    column[UserTable.username] = username
                }[UserTable.id]
        }
    }

    override suspend fun read(login: String): SelectUserDto? = dbQuery {
        UserTable.selectAll()
            .where { UserTable.login eq login }
            .map { result ->
                SelectUserDto(
                    login = result[UserTable.login],
                    username = result[UserTable.username]
                )
            }
            .singleOrNull()
    }

    override suspend fun update(updatedUser: UpdateUserDto) {
        dbQuery {
            UserTable.update({ UserTable.login eq updatedUser.login }) { column ->
                updatedUser.newLogin?.let { newLogin -> column[login] = newLogin }
                updatedUser.username?.let { newUsername -> column[username] = newUsername }
            }
        }
    }

    private suspend fun <T> dbQuery(query: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { query() }
}