package com.cakkie.ui.screens.search

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.cakkie.utill.isVideoUrl
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchItem(
    listing: Listing,
    shouldPlay: Boolean,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val isPlaying = shouldPlay && !expanded
    val pageState =
        rememberPagerState(pageCount = { if (listing.media.isEmpty()) 1 else listing.media.size })


    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(CakkieBackground)
            .padding(1.dp)
            .clickable { expanded = !expanded }
    ) {
        val mediaUrl = listing.media.firstOrNull()

        if (mediaUrl != null && mediaUrl.isVideoUrl()) {
            VideoContent(
                context,
                progressiveMediaSource,
                mediaUrl,
                shouldPlay,
                expanded,
                isPlaying,
                screenHeight,
                navigator,
                listing
            )
        } else if (mediaUrl != null) {
            ImageContent(mediaUrl, listing)
        }
    }

    if (expanded) {
        ExpandImage(
            item = listing,
            expanded = expanded,
            onDismiss = { expanded = false },
            navigator = navigator,
            showDetails = true,
            pageState = pageState
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoContent(
    context: Context,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    mediaUrl: String,
    shouldPlay: Boolean,
    expanded: Boolean,
    isPlaying: Boolean,
    screenHeight: Dp,
    navigator: DestinationsNavigator,
    listing: Listing
) {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaSource =
                progressiveMediaSource.createMediaSource(MediaItem.fromUri(mediaUrl))
            setMediaSource(mediaSource)
            playWhenReady = shouldPlay
            volume = if (expanded) 1f else 0f
        }
    }

    VideoPlayer(
        exoPlayer = exoPlayer,
        isPlaying = isPlaying,
        mute = true,
        isSearchScreen = true,
        vResizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL,
        modifier = Modifier
            .heightIn(max = screenHeight * 0.6f)
            .clickable {
                navigator.navigate(
                    CakespirationDestination(
                        id = listing.id,
                        item = listing
                    )
                )
            }
    )

    LikeRow(listing)
}

@Composable
private fun ImageContent(mediaUrl: String, listing: Listing) {
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {

        AsyncImage(
            model = mediaUrl,
            contentDescription = "Image",
            onState = { isLoading = it is AsyncImagePainter.State.Loading },
            modifier = Modifier
                .fillMaxSize()
                .placeholder(
                    visible = isLoading,
                    color = CakkieBrown.copy(0.8f),
                    highlight = PlaceholderHighlight.shimmer()
                ),
            contentScale = ContentScale.Crop
        )

        Icon(
            painter = painterResource(id = R.drawable.grid_icon_picture),
            contentDescription = "Image icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(5.dp)
                .size(20.dp),
            tint = CakkieBackground
        )

        LikeRow(listing = listing)
    }
}

@Composable
private fun LikeRow(listing: Listing) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.gridicons_heart),
                contentDescription = "Likes icon",
                modifier = Modifier
                    .size(12.dp),
                tint = Error
            )

            Text(
                text = listing.totalLikes.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}



