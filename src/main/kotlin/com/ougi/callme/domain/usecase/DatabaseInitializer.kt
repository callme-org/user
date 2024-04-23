package com.ougi.callme.domain.usecase

import com.ougi.callme.domain.repository.DatabaseRepository

interface DatabaseInitializer {

    fun initialize()

}

class DatabaseInitializerImpl(
    private val databaseRepository: DatabaseRepository
) : DatabaseInitializer {

    override fun initialize() = databaseRepository.initDatabase()


}