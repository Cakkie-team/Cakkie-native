package com.cakkie.ui.screens.cakespiration

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.cakkie.di.CakkieApp
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.utill.toObject
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@Destination
@Composable
fun EditVideo(file: String) {
    val viewModal: ExploreViewModal = koinViewModel()
    var isMuted by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val media = file.toObject(MediaModel::class.java)
    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(CakkieApp.simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val source =
                    progressiveMediaSource
                        .createMediaSource(MediaItem.fromUri(media.uri))
                setMediaSource(source)
            }
    }


    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VideoPlayer(
            exoPlayer = exoPlayer,
            isPlaying = true,
            mute = isMuted,
            onMute = {
                isMuted = it
            },
            vResizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isMuted = !isMuted
                }
        )
    }
}