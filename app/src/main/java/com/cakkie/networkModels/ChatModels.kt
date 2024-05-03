package com.cakkie.networkModels

import android.os.Parcelable
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
    val media: List<String> = listOf(),
    val replyToId: String = "",
    val text: String = "",
    val updatedAt: String = "",
    val userId: String = "",
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