package com.cakkie.networkModels

import com.cakkie.data.db.models.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val isNewDevice: Boolean = false,
    val message: String = "",
    val token: String = "",
    val user: User = User()
)