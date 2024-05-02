package com.ougi.callme.data.model.table

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("users") {

    val login = varchar("login", 128).uniqueIndex()
    val username = varchar("username", 128).nullable()

}