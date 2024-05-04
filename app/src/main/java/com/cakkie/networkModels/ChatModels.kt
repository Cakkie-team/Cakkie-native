package com.cakkie.networkModels

import android.os.Parcelable
import com.cakkie.data.db.models.User
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Conversation(
    val byUserId: String = "",
    val createdAt: String = "",
    val deletedBy: List<String> = listOf(),
    val forAdmins: Boolean = false,
    val id: String = "",
    val isActive: Boolean = false,
    val recentMessage: Message = Message(),
    val shopId: String = "",
    val updatedAt: String = "",
    val unreadCount: Int = 0,
    val display: Display = Display(),
) : Parcelable

@Serializable
@Parcelize
data class Display(
    val image: String = "",
    val isOnline: Boolean = false,
    val name: String = "",
) : Parcelable

@Serializable
@Parcelize
data class Message(
    val conversationId: String = "",
    val createdAt: String = "",
    val id: String = "",
    val isDeleted: Boolean = false,
    val isDelivered: Boolean = false,
    val isRead: Boolean = false,
    val media: String? = null,
    val replyToId: String = "",
    val replyTo: Message? = null,
    val text: String = "",
    val updatedAt: String = "",
    val userId: String = "",
    val user: User = User()
) : Parcelable


@Serializable
data class ConversationResponse(
    val data: List<Conversation> = listOf(),
    val meta: Pagination = Pagination(),
)

@Serializable
data class MessageResponse(
    val data: List<Conversation> = listOf(),
    val meta: Pagination = Pagination(),
)