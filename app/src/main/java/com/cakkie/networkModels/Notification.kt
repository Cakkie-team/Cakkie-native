package com.cakkie.networkModels

import android.os.Parcelable
import com.cakkie.data.db.models.User
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Notification(
    val createdAt: String,
    val extra: String,
    val id: String,
    val message: String,
    val read: Boolean,
    val title: String,
    val type: String,
    val updatedAt: String,
    val user: User,
    val userId: String
) : Parcelable


@Serializable
data class NotificationResponse(
    val data: List<Notification> = listOf(),
    val meta: Pagination = Pagination(),
)