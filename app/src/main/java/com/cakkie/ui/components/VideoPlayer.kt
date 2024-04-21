package com.cakkie.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    showControls: Boolean = false,
    mute: Boolean = true,
    vResizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_FIT,
    onMute: (Boolean) -> Unit = {},
    isCakespiration: Boolean = false
) {
    val context = LocalContext.current
    var _isPlaying by remember {
        mutableStateOf(isPlaying)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = mute) {
        if (mute) {
            exoPlayer.volume = 0f
        } else {
            exoPlayer.volume = 1f
        }
    }

    exoPlayer.playWhenReady = _isPlaying
    exoPlayer.videoScalingMode =
        C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    exoPlayer.volume = if (mute) 0f else 1f
    exoPlayer.prepare()
    LaunchedEffect(isPlaying) {
        _isPlaying = isPlaying
        if (isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(factory = {
            PlayerView(context).apply {
                hideController()
                useController = showControls
                resizeMode = vResizeMode
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        })
        if (exoPlayer.isLoading && exoPlayer.isPlaying.not()) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                trackColor = CakkieBrown.copy(0.6f),
                modifier = Modifier.size(80.dp),
            )
        }


        //mute and unmute
        IconButton(
            onClick = {
                onMute.invoke(exoPlayer.volume > 0f)
            }, modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(0.5f)
                ), shape = CircleShape
            ) {
                Icon(
                    painter =
                    painterResource(id = if (!mute) R.drawable.sound else R.drawable.mute),
                    contentDescription = "Speaker",
                    tint = CakkieBackground,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                )
            }
        }

        //play and pause
        if (_isPlaying.not() && isCakespiration.not()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown
                ), shape = CircleShape,
                onClick = {
                    _isPlaying = !_isPlaying
                }, modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    painter =
                    painterResource(id = R.drawable.play),
                    contentDescription = "Speaker",
                    tint = CakkieBackground,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                )
            }
        }
    }

    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (!isPlaying) return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                else -> exoPlayer.pause()
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }
}