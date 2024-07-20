package com.cakkie.ui.screens.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Conversation
import com.cakkie.networkModels.ConversationResponse
import com.cakkie.networkModels.Proposal
import com.cakkie.socket.SocketClient
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class ChatViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val socketClient: SocketClient by inject()
    private val _user = MutableLiveData<User>()
    private val _conversations = MutableLiveData<ConversationResponse>()


    val conversations = _conversations
    val user = _user
    val socket = socketClient.socket


    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    //start chat
    fun startChat(
        forAdmins: Boolean,
        shopId: String?,
        content: String,
        media: String?,
        proposalId: String?
    ) = NetworkCalls.post<Conversation>(
        endpoint = Endpoints.START_CHAT,
        body = listOf(
            "forAdmins" to forAdmins,
            "shopId" to shopId,
            "content" to content,
            "media" to media,
            "proposalId" to proposalId
        )
    )


    fun getConversations(search: String = "", page: Int = 0, size: Int = 20) =
        NetworkCalls.get<ConversationResponse>(
            endpoint = Endpoints.GET_CONV(search, page, size),
            body = listOf()
        ).addOnSuccessListener {
            _conversations.value = it
        }

    fun getSupport(id: String) {
        val data = JSONObject()
        data.put("id", id)
        socketClient.socket.emit("getSupport", data)
    }

    fun getConvr(id: String, useId: String) {
        val data = JSONObject()
        data.put("id", id)
        data.put("userId", useId)
        socketClient.socket.emit("getConvr", data)
    }

    fun readChat(userId: String, messageId: String) {
        val data = JSONObject()
        data.put("userId", userId)
        data.put("messageId", messageId)
        socketClient.socket.emit("readMessage", data)
    }

    fun deliveredChat(userId: String, messageId: String) {
        val data = JSONObject()
        data.put("userId", userId)
        data.put("messageId", messageId)
        socketClient.socket.emit("deliveredMessage", data)
    }

    fun uploadImage(image: File, path: String, fileName: String) =
        NetworkCalls.uploadFile(
            endpoint = Endpoints.UPLOAD_IMAGE(path, fileName), media = image
        )

    fun deleteChat(userId: String, messageId: String) {
        val data = JSONObject()
        data.put("userId", userId)
        data.put("messageId", messageId)
        socketClient.socket.emit("deleteMessage", data)
    }

    fun getMessages(id: String, page: Int = 0, size: Int = 20) {
        val data = JSONObject()
        data.put("conversationId", id)
        data.put("page", page)
        data.put("pageSize", size)
        socketClient.socket.emit("getConversationMessages", data)
    }

    fun sendMessages(
        userId: String,
        conversationId: String?,
        text: String,
        media: String?,
        replyTo: String?
    ) {
        val data = JSONObject()
        data.put("conversationId", conversationId)
        data.put("userId", userId)
        data.put("replyTo", replyTo)
        data.put("text", text)
        data.put("media", media)
        socketClient.socket.emit("sendMessage", data)
    }

    fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    fun getProposal(id: String) = NetworkCalls.get<Proposal>(
        endpoint = Endpoints.GET_PROPOSAL(id),
        body = listOf()
    )

    init {
        getProfile()
        getUser()
    }
}