package com.cakkie.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    isPlaying: Boolean = false,
    showControls: Boolean = false,
    mute: Boolean = false,
    onMute: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val lifecycleOwner = LocalLifecycleOwner.current
    var isMuted by rememberSaveable {
        mutableStateOf(mute)
    }
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }
    LaunchedEffect(key1 = isMuted) {
        if (isMuted) {
            exoPlayer.volume = 0f
        } else {
            exoPlayer.volume = 1f
        }
    }
    exoPlayer.playWhenReady = isPlaying
    exoPlayer.videoScalingMode =
        C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(factory = {
            PlayerView(context).apply {
                hideController()
                useController = showControls
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }, modifier = Modifier.heightIn(max = screenWidth + 100.dp))
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
                onMute.invoke(!isMuted)
                isMuted = !isMuted
            }, modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Black.copy(0.5f), CircleShape)
                .clip(CircleShape)
        ) {
            Icon(
                painter =
                painterResource(id = if (exoPlayer.volume > 0f) R.drawable.sound else R.drawable.mute),
                contentDescription = "Speaker",
                tint = CakkieBackground,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
//            if (!isPlaying) return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }
}