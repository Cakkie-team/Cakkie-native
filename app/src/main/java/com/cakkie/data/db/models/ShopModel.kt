package com.cakkie.data.db.models

data class ShopModel(
    val address: String,
    val city: String,
    val country: String,
    val createdAt: String,
    val description: String,
    val followers: Int,
    val followingCount: Int,
    val id: String,
    val image: String,
    val isFollowing: Boolean,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val state: String,
    val updatedAt: String,
    val user: User,
    val userId: String
)