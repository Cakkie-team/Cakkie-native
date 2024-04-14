package com.cakkie.ui.screens.cakespiration

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.cakkie.R
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.toObject
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Destination
@Composable
fun CreateCakespiration(file: String, navigator: DestinationsNavigator) {
    var isMuted by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val media = file.toObject(MediaModel::class.java)
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory =
                    DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory =
                    DefaultDataSource.Factory(
                        context,
                        defaultDataSourceFactory
                    )
                val source =
                    ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(media.uri))
                setMediaSource(source)
                prepare()
            }
    }


    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            IconButton(modifier = Modifier
                .align(Alignment.CenterStart), onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = "New Cakespiration",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                color = CakkieBrown,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black)
                .fillMaxSize(0.65f),
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
}