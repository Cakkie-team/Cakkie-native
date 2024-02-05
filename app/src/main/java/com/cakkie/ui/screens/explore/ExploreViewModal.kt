package com.cakkie.ui.screens.explore

import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.CommentResponse
import com.cakkie.networkModels.Listing
import com.cakkie.networkModels.ListingResponse
import com.cakkie.socket.SocketClient
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(UnstableApi::class)
class ExploreViewModal : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val socketClient: SocketClient by inject()
    private val _user = MutableLiveData<User>()
    private val _listings = MutableLiveData<ListingResponse>()


    val listings = _listings
    val user = _user
    val socket = socketClient.socket

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    fun getListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_LISTINGS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _listings.value = it
    }

    fun getComments(listingId: String, page: Int = 0, size: Int = 20) =
        NetworkCalls.get<CommentResponse>(
            endpoint = Endpoints.GET_COMMENTS(listingId, page, size),
            body = listOf()
        )

    fun getListing(id: String) = NetworkCalls.get<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf()
    )

    fun likeListing(id: String, userId: String) {
        val data = JSONObject()
        data.put("listingId", id)
        data.put("userId", userId)
        socketClient.socket.emit("like-listing", data)
    }

    fun starListing(id: String, userId: String) {
        val data = JSONObject()
        data.put("listingId", id)
        data.put("userId", userId)
        socketClient.socket.emit("star-listing", data)
    }

    fun commentListing(id: String, userId: String, comment: String) {
        val data = JSONObject()
        data.put("listingId", id)
        data.put("userId", userId)
        data.put("comment", comment)
        socketClient.socket.emit("comment-listing", data)
    }

    init {
        getUser()
        getListings()
    }
}

data class VideoModel(
    val exoPlayer: ExoPlayer,
    val listings: Listing
)