package com.cakkie.ui.screens.explore

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ListingResponse
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.ListingRepository
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.CommentResponse
import com.cakkie.networkModels.Order
import com.cakkie.networkModels.Pagination
import com.cakkie.socket.SocketClient
import com.cakkie.utill.Constants
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import com.cakkie.utill.VideoPreLoadingService
import com.cakkie.utill.isVideoUrl
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.util.Locale

@OptIn(UnstableApi::class)
class ExploreViewModal : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val socketClient: SocketClient by inject()
    private val _user = MutableLiveData<User>()
    private val _listings = MutableLiveData<ListingResponse>()
    private val _cakespiration = MutableLiveData<ListingResponse>()
    private val listingRepository: ListingRepository by inject()
    private val _pagination = MutableLiveData(Pagination())


    val listings = _listings
    val cakespiration = _cakespiration
    val pagination = _pagination
    val user = _user
    val socket = socketClient.socket

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    fun getListings(context: Context, page: Int = 0, size: Int = 20) =
        NetworkCalls.get<ListingResponse>(
            endpoint = Endpoints.GET_LISTINGS(page, size),
            body = listOf()
        ).addOnSuccessListener {
            viewModelScope.launch(Dispatchers.Main) {
                listingRepository.addListings(it.data)
                _pagination.value = it.meta
                val videoList = it.data.filter {
                    it.media.any { it.isVideoUrl() }
                }.map { it.media.filter { it.isVideoUrl() }.joinToString(",") }
                    .joinToString(",")
//                        Timber.d("Video list: ${videoList.split(",")}")
                val array = arrayListOf<String>()
                array.addAll(videoList.split(","))

                //create intent
                val preloadingServiceIntent =
                    Intent(context, VideoPreLoadingService::class.java)
                preloadingServiceIntent.putStringArrayListExtra(
                    Constants.VIDEO_LIST,
                    array
                )

                //start intent
                context.startService(preloadingServiceIntent)
            }
        }


    fun getCakespirations(context: Context, page: Int = 0, size: Int = 20) =
        NetworkCalls.get<ListingResponse>(
            endpoint = Endpoints.GET_CAKESPIRATIONS(page, size),
            body = listOf()
        ).addOnSuccessListener {
            viewModelScope.launch(Dispatchers.Main) {
                _cakespiration.value = it
                val videoList = it.data.filter {
                    it.media.any { it.isVideoUrl() }
                }.map { it.media.filter { it.isVideoUrl() }.joinToString(",") }
                    .joinToString(",")
//                        Timber.d("Video list: ${videoList.split(",")}")
                val array = arrayListOf<String>()
                array.addAll(videoList.split(","))

                //create intent
                val preloadingServiceIntent =
                    Intent(context, VideoPreLoadingService::class.java)
                preloadingServiceIntent.putStringArrayListExtra(
                    Constants.VIDEO_LIST,
                    array
                )

                //start intent
                context.startService(preloadingServiceIntent)
            }
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

    fun likeComment(id: String, userId: String) {
        val data = JSONObject()
        data.put("commentId", id)
        data.put("userId", userId)
        socketClient.socket.emit("like-comment", data)
    }

    fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    fun createOrder(
        listingId: String,
        shopId: String, quantity: Int,
        unitPrice: Double,
        deliveryAddress: String,
        deliveryFee: Double,
        latitude: Double,
        longitude: Double,
        currencySymbol: String,
        pin: String
    ) = NetworkCalls.post<Order>(
        endpoint = Endpoints.CREATE_ORDER,
        body = listOf(
            Pair("listingId", listingId),
            Pair("shopId", shopId),
            Pair("quantity", quantity),
            Pair("unitPrice", unitPrice),
            Pair("deliveryAddress", deliveryAddress),
            Pair("deliveryFee", deliveryFee),
            Pair("latitude", latitude),
            Pair("longitude", longitude),
            Pair("currencySymbol", currencySymbol.uppercase(Locale.ROOT)),
            Pair("pin", pin)
        )
    )

    init {
        getProfile()
        getUser()
//        getListings()
        viewModelScope.launch {
            listingRepository.getListings().asLiveData().observeForever {
                if (it.isNotEmpty()) {
                    _pagination.observeForever { pagination ->
                        if (pagination != null) {
//                            Timber.d("Pagination: $pagination and data: ${it}")
                            _listings.value = ListingResponse(data = it, meta = pagination)
                            if (_cakespiration.value == null) {
                                _cakespiration.value = ListingResponse(
                                    data = it.filter { it.type != "LISTING" },
                                    meta = pagination
                                )
                            }
                        }
                    }
                }
            }
        }

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Timber.d("Token: $it")
            NetworkCalls.post<User>(
                endpoint = Endpoints.SEND_FCM_TOKEN(it),
                body = listOf()
            ).addOnSuccessListener { response ->
                Timber.d("Token sent successfully")
            }.addOnFailureListener { exception ->
                Timber.e(exception)
            }
        }
    }
}