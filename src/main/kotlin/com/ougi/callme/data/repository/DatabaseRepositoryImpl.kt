package com.ougi.callme.data.repository

import com.ougi.callme.data.model.table.UserTable
import com.ougi.callme.domain.repository.DatabaseRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseRepositoryImpl : DatabaseRepository {

    override fun initDatabase() {
        val host = System.getenv(DATABASE_HOST)
        val port = System.getenv(DATABASE_PORT)
        val name = System.getenv(DATABASE_NAME)

        Database.connect(
            url = "jdbc:mysql://$host:$port/$name$JDBC_PARAMS",
            user = System.getenv(DATABASE_USERNAME),
            password = System.getenv(DATABASE_PASSWORD),
            driver = DB_DRIVER_NAME
        )
            .let { database ->
                transaction(database) {
                    SchemaUtils.createMissingTablesAndColumns(UserTable)
                }
            }
    }

    private companion object {
        private const val DATABASE_NAME = "DATABASE_NAME"
        private const val DATABASE_HOST = "DATABASE_HOST"
        private const val DATABASE_PORT = "DATABASE_PORT"
        private const val DATABASE_USERNAME = "DATABASE_USERNAME"
        private const val DATABASE_PASSWORD = "DATABASE_PASSWORD"

        private const val JDBC_PARAMS = "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"

        private const val DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver"
    }
}