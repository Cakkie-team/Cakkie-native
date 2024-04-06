package com.cakkie.data.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "user")
@Parcelize
@Serializable
data class User(
    val address: String = "",
    val bio: String? = null,
    val city: String = "",
    val country: String = "",
    val coverImage: List<String> = listOf(""),
    val createdAt: String = "",
    val dateOfBirth: String? = null,
    val email: String = "",
    val emailVerifiedAt: String? = null,
    val firstName: String = "",
    val gender: String = "",
    val hasShop: Boolean = false,
    val isDeleted: Boolean = false,
    val isOnline: Boolean = false,
    val earningRate: Double = 0.0,
    val lastMine: String = "",
    @PrimaryKey
    val id: String = "",
    val isDriver: Boolean = false,
    val lastName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val otpCounter: Int = 0,
    val password: String = "",
    val phoneNumber: String? = null,
    val phoneNumberVerifiedAt: String? = null,
    val pin: String? = null,
    val profileImage: String = "",
    val referralCode: String = "",
    val referrerId: String? = null,
    val state: String = "",
    val updatedAt: String = "",
    val userType: String = "",
    val username: String = "",
    val websocketId: String? = null
) : Parcelable