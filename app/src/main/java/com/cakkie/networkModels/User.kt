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

@Serializable
data class UserResponse(
    val data: List<User> = listOf(),
    val meta: Pagination = Pagination(),
    val total: Double = 0.0
)