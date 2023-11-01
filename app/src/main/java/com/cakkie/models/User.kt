package com.cakkie.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val bio: String?,
    val city: String,
    val country: String,
    val coverImage: List<String>,
    val createdAt: String,
    val dateOfBirth: String?,
    val email: String,
    val emailVerifiedAt: String?,
    val firstName: String,
    val gender: String,
    val hasShop: Boolean,
    val id: String,
    val isDriver: Boolean,
    val lastName: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val otpCounter: Int,
    val password: String,
    val phoneNumber: String?,
    val phoneNumberVerifiedAt: String?,
    val pin: String?,
    val profileImage: String,
    val referralCode: String,
    val referrerId: String?,
    val state: String,
    val updatedAt: String,
    val userType: String,
    val username: String,
    val websocketId: String?,
)
