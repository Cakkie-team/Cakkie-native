package com.cakkie.ui.screens.cakespiration

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.cakkie.R
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CreateCakespirationDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.toJson
import com.cakkie.utill.toObject
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Destination
@Composable
fun EditVideo(file: String, navigator: DestinationsNavigator) {
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

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.End
        ) {
            Card(
                onClick = {
                    navigator.navigate(CreateCakespirationDestination(file = media.toJson()))
                },
                modifier = Modifier
                    .width(74.dp)
                    .height(35.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.next),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBackground,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .rotate(180f),
                        tint = CakkieBackground
                    )
                }
            }
        }

        IconButton(
            onClick = { navigator.popBackStack() }, modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = "Arrow",
                    tint = CakkieBackground,
                )
            }

        }
    }
}