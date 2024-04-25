package com.ougi.callme.presentation

import com.ougi.callme.di.appModule
import com.ougi.callme.domain.usecase.DatabaseInitializer
import com.ougi.callme.presentation.routing.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    installPlugins()
    configureDatabase()
    configureRouting()
}

private fun Application.installPlugins() {
    install(ContentNegotiation) {
        json()
    }
    install(Koin) {
        modules(appModule)
    }
}

private fun Application.configureDatabase() {
    val databaseInitializer by inject<DatabaseInitializer>()
    databaseInitializer.initialize()
}
