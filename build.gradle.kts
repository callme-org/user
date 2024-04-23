plugins {
    val kotlinVersion = libs.versions.core.kotlin.get()
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("io.ktor.plugin") version libs.versions.core.ktor.get()
}

group = "com.ougi.callme"
version = "0.0.1"

application {
    mainClass.set("com.ougi.callme.presentation.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.core.jvm)
    implementation(libs.ktor.netty.jvm)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.serialization)

    implementation(libs.db.exposed.core)
    implementation(libs.db.exposed.jdbc)
    implementation(libs.db.h2)

    implementation(libs.koin)
}
