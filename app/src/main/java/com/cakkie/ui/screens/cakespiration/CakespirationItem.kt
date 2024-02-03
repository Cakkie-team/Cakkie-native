package com.cakkie.ui.screens.cakespiration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.MoreOptionsDestination
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.utill.formatDate
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CakespirationItem(
    item: Listing,
    navigator: DestinationsNavigator,
    shouldPlay: Boolean,
    isMuted: Boolean,
    onMute: (Boolean) -> Unit,
    exoPlayer: ExoPlayer
) {
    var maxLines by rememberSaveable { mutableIntStateOf(1) }
    var isLiked by rememberSaveable { mutableStateOf(item.isLiked) }
    var isStarred by rememberSaveable { mutableStateOf(item.isStarred) }

    val context = LocalContext.current

//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .build()
//            .apply {
//                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
//                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
//                    context,
//                    defaultDataSourceFactory
//                )
//                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(item.media.first()))
//                setMediaSource(source)
//                prepare()
//            }
//    }
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .build()
//            .apply {
//
//                val source = progressiveMediaSource
//                    .createMediaSource(MediaItem.fromUri(item.media[0]))
//                setMediaSource(source)
//                prepare()
//            }
//    }
//    exoPlayer.playWhenReady = false
//    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
//    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
//
//    val isPlaying = remember {
//        derivedStateOf {
//            exoPlayer.isPlaying
//        }
//    }
//    LaunchedEffect(key1 = shouldPlay) {
//        if (shouldPlay) {
//            exoPlayer.play()
//        } else {
//            exoPlayer.pause()
//        }
//    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        AndroidView(factory = {
//            PlayerView(context).apply {
//                hideController()
//                useController = false
//                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//                player = exoPlayer
//                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
//            }
//        })
        VideoPlayer(
            exoPlayer = exoPlayer,
            isPlaying = shouldPlay,
            mute = isMuted,
            onMute = onMute,
            vResizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onMute.invoke(!isMuted)
                }
        )

//        DisposableEffect(Unit) {
//            onDispose { exoPlayer.release() }
//        }

//        Box(modifier = Modifier
//            .fillMaxSize()
////            .background(Color.Black.copy(alpha = 0.5f))
//            .clickable {
//                if (isPlaying.value) exoPlayer.pause() else exoPlayer.play()
//            }
//        )

        Column(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 28.dp)
                .offset(x = (-14).dp, y = (-16).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(
                    id =
                    if (isLiked) R.drawable.gridicons_heart else R.drawable.heart
                ),
                contentDescription = "heart",
                tint = if (isLiked) Color.Red else CakkieBackground,
                modifier = Modifier
                    .clickable {
                        isLiked = !isLiked
                    }
                    .padding(8.dp)
            )
            Text(
                text = item.totalLikes.toString(),
                color = CakkieBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.comment),
                contentDescription = "comment",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { navigator.navigate(CommentDestination) }
                    .padding(8.dp)
            )
            Text(
                text = item.commentCount.toString(),
                color = CakkieBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "share",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { }
                    .padding(8.dp)
            )
            Icon(
                painter = painterResource(
                    id =
                    if (isStarred) R.drawable.star_filled else R.drawable.star_add
                ),
                contentDescription = "star",
                tint = if (isStarred) CakkieYellow else CakkieBackground,
                modifier = Modifier
                    .clickable {
                        isStarred = !isStarred
                    }
                    .padding(8.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.options),
                contentDescription = "more",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { navigator.navigate(MoreOptionsDestination) }
                    .padding(8.dp)
            )
        }

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 28.dp)
                .offset(x = 16.dp, y = (-16).dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = item.shop.name.ifEmpty { "https://source.unsplash.com/60x60/?profile" },
                    contentDescription = "shop logo",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(ProfileDestination)
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = item.shop.name.ifEmpty { "Cake Paradise" },
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = CakkieBackground
                    )
                    Text(
                        text = item.createdAt.formatDate(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = CakkieBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(0.8f),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                color = CakkieBackground
            )
            if (maxLines == 1) {
                Text(
                    text = stringResource(id = R.string.see_more),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clickable { maxLines = Int.MAX_VALUE },
                    color = CakkieBackground
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth(0.6f)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(40))
                    .clip(RoundedCornerShape(40)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.music),
                    contentDescription = "music",
                    modifier = Modifier
                        .padding(6.dp)
                        .size(18.dp)
                )
                Text(
                    text = "Imagine Dragons - Believer",
                    style = MaterialTheme.typography.bodySmall,
                    color = CakkieBackground,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}