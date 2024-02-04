package com.cakkie.ui.screens.explore

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Listing
import com.cakkie.networkModels.ListingResponse
import com.cakkie.socket.SocketClient
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

@OptIn(UnstableApi::class)
class ExploreViewModal : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val socketClient: SocketClient by inject()
    private val _user = MutableLiveData<User>()
    private val _listings = MutableLiveData<ListingResponse>()
    private val _videos = MutableLiveData<List<VideoModel>>()


    val listings = _listings
    val user = _user
    val videos = _videos


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

    fun getListing(id: String) = NetworkCalls.get<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf()
    )

    fun getPlayer(url: String, context: Context) = ExoPlayer.Builder(context).build().apply {
        val defaultDataSourceFactory = DefaultDataSource.Factory(context)
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSource.Factory(
                context,
                defaultDataSourceFactory
            )
        val progressiveMediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
        val source = progressiveMediaSource
            .createMediaSource(MediaItem.fromUri(url))
        setMediaSource(source)
        prepare()
    }

    fun updateVideos(context: Context, listings: List<Listing>) {
        val list = mutableListOf<VideoModel>()
        _videos.value?.let { list.addAll(it.toList()) }
        val defaultDataSourceFactory = DefaultDataSource.Factory(context)
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSource.Factory(
                context,
                defaultDataSourceFactory
            )
        val progressiveMediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)

        val videos = listings.map {
            val exoPlayer =
                ExoPlayer.Builder(context)
                    .build()
                    .apply {

                        val source = progressiveMediaSource
                            .createMediaSource(MediaItem.fromUri(it.media[0]))
                        setMediaSource(source)
                        prepare()
                    }
            VideoModel(exoPlayer, it)
        }
        list.addAll(videos)
        _videos.value = list
    }

    init {
        getUser()
        getListings()
        socketClient.socket.on(Socket.EVENT_CONNECT) {
            Timber.d("socket connected")
        }
    }
}

data class VideoModel(
    val exoPlayer: ExoPlayer,
    val listings: Listing
)