package com.ougi.callme.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class User(
    @SerialName("login")
    val login: String,
    @SerialName("username")
    val username: String?,
)