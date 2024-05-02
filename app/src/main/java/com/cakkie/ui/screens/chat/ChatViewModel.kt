package com.cakkie.ui.screens.chat

import androidx.lifecycle.ViewModel
import com.cakkie.data.db.models.User
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import org.koin.core.component.KoinComponent
import timber.log.Timber

class ChatViewModel : ViewModel(), KoinComponent {

    //start chat
    fun startChat() = NetworkCalls.post<User>(
        endpoint = Endpoints.START_CHAT,
        body = listOf()
    ).addOnSuccessListener { response ->
        Timber.d("Token sent successfully")
    }.addOnFailureListener { exception ->
        Timber.e(exception)
    }

}