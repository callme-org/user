package com.ougi.callme.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserToUpdate(
    @SerialName("login")
    val login: String,
    @SerialName("newLogin")
    val newLogin: String?,
    @SerialName("username")
    val username: String?,
)