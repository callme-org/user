package com.ougi.callme.data.model.table

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val id = integer("id").autoIncrement()
    val login = varchar("login", 128).uniqueIndex()
    val username = varchar("username", 128).nullable()

    override val primaryKey = PrimaryKey(id)

}