package com.cakkie.ui.screens.explore

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.appodeal.ads.Appodeal
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.databinding.AdviewBinding
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.ItemDetailsDestination
import com.cakkie.ui.screens.destinations.MoreOptionsDestination
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.formatDate
import com.cakkie.utill.isVideoUrl
import com.cakkie.utill.toObject
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ExploreItem(
    item: Listing = Listing(),
    shouldPlay: Boolean = false,
    isMuted: Boolean = false,
    onMute: (Boolean) -> Unit = {},
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator,
    index: Int = 0
) {
    val viewModal: ExploreViewModal = koinViewModel()
    val user = viewModal.user.observeAsState().value
    val context = LocalContext.current
    var listing by rememberSaveable { mutableStateOf(item) }
    var maxLines by rememberSaveable { mutableIntStateOf(1) }
    var isLiked by rememberSaveable { mutableStateOf(listing.isLiked) }
    var isStarred by rememberSaveable { mutableStateOf(listing.isStarred) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val isSponsored by remember { mutableStateOf(false) }
    val pageState =
        rememberPagerState(pageCount = { if (listing.media.isEmpty()) 1 else listing.media.size })
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val isPlaying = remember {
        derivedStateOf { shouldPlay && !expanded }
    }.value

    DisposableEffect(Unit) {
        viewModal.socket.on("like-${listing.id}") {
            val newListing = it[0].toString().toObject(Listing::class.java)
            listing = newListing
        }
        viewModal.socket.on("star-${listing.id}") {
            val newListing = it[0].toString().toObject(Listing::class.java)
            listing = newListing
        }
        viewModal.socket.on("comment-${listing.id}") {
            val newListing = listing.copy(commentCount = listing.commentCount + 1)
            listing = newListing
        }
        onDispose {
            viewModal.socket.off("like-${listing.id}")
            viewModal.socket.off("star-${listing.id}")
            viewModal.socket.off("comment-${listing.id}")
        }
    }


    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            Modifier
                .padding(start = 16.dp, bottom = 2.dp, end = 2.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                var isLoading by remember {
                    mutableStateOf(false)
                }
                AsyncImage(
                    model = listing.shop.image,
                    contentDescription = "profile pic",
                    onState = {
                        //update isLoaded
                        isLoading = it is AsyncImagePainter.State.Loading
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(
                                ProfileDestination(
                                    id = listing.shopId,
                                    shop = listing.shop
                                )
                            )
                        }
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = CakkieBrown.copy(0.8f)
                        )
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = listing.shop.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isSponsored) stringResource(id = R.string.sponsored) else listing.createdAt.formatDate(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            IconButton(onClick = { navigator.navigate(MoreOptionsDestination) }) {
                Image(
                    painter = painterResource(id = R.drawable.options),
                    contentDescription = "notification",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if (isSponsored) {
            Row(
                Modifier
                    .background(CakkieBrown)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.view_profile),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
                    color = CakkieBackground
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "arrow back",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f),
                    tint = CakkieBackground
                )
            }
        }
        HorizontalPager(state = pageState) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(screenHeight - 100.dp)
//                    .clickable { expanded = !expanded }
//                    .background(Color.Black.copy(alpha = 0.6f))
            ) {

                if (listing.media[it].isVideoUrl()) {
                    val exoPlayer = remember {
                        ExoPlayer.Builder(context).build().apply {
                            playWhenReady = false
                            val source = progressiveMediaSource
                                .createMediaSource(MediaItem.fromUri(listing.media[it]))
                            setMediaSource(source)
                        }
                    }

                    VideoPlayer(
                        exoPlayer = exoPlayer,
                        isPlaying = isPlaying,
                        mute = isMuted,
                        onMute = onMute,
                        vResizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL,
                        modifier = Modifier
                            .heightIn(max = screenHeight * 0.6f)
                            .clickable {
                                navigator.navigate(
                                    CakespirationDestination(id = listing.id, item = listing)
                                )
                            }
                    )
                } else {
                    var isLoading by remember {
                        mutableStateOf(false)
                    }
                    AsyncImage(
                        model = listing.media[it],
                        contentDescription = "cake",
                        onState = {
                            //update isLoaded
                            isLoading = it is AsyncImagePainter.State.Loading
                        },
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .heightIn(max = screenHeight * 0.65f)
                            .placeholder(
                                visible = isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = CakkieBrown.copy(0.8f)
                            )
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        }
        Row(
            Modifier
                .padding(start = 8.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(
                        id =
                        if (isLiked) R.drawable.gridicons_heart else R.drawable.heart
                    ),
                    contentDescription = "heart",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (user != null) viewModal.likeListing(listing.id, user.id)
                            isLiked = !isLiked
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.comment),
                    contentDescription = "comment",
                    modifier = Modifier
                        .clickable { navigator.navigate(CommentDestination(item = listing)) }
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(
                        id =
                        if (isStarred) R.drawable.star_filled else R.drawable.star_add
                    ),
                    contentDescription = "star",
                    modifier = Modifier
                        .clickable {
                            if (user != null) viewModal.starListing(listing.id, user.id)
                            isStarred = !isStarred
                        }
                        .size(24.dp)
                )
            }
            if (pageState.pageCount > 1) {
                HorizontalPagerIndicator(
                    pagerState = pageState,
                    activeColor = CakkieBrown,
                    spacing = 8.dp,
                    indicatorWidth = 5.dp,
                    indicatorHeight = 5.dp,
                    pageCount = pageState.pageCount,
                    modifier = Modifier.offset(x = (-24).dp)
                )
            }
            Card(
                onClick = {
                    when (item.type) {
                        "LISTING" -> navigator.navigate(
                            ItemDetailsDestination(
                                id = listing.id,
                                item = listing
                            )
                        )

                        else -> {}
                    }
                },
                modifier = Modifier
                    .width(if (item.type == "LISTING") 64.dp else 74.dp)
                    .height(24.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown
                )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (item.type == "LISTING") stringResource(id = R.string.order) else stringResource(
                            id = R.string.request
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBackground
                    )
                }
            }
        }
        Text(
            text = "${listing.totalLikes} Likes",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp),
            color = CakkieBrown
        )
        Text(
            text = listing.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
        if (maxLines == 1) {
            Text(
                text = stringResource(id = R.string.see_more),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable { maxLines = Int.MAX_VALUE }
                    .padding(start = 16.dp),
                color = CakkieBrown
            )
        }
        Text(
            text = "View all ${listing.commentCount} comments",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clickable { navigator.navigate(CommentDestination(listing)) }
                .padding(start = 16.dp, end = 16.dp, top = 5.dp),
            color = CakkieBrown
        )

    }
    if (index % 3 == 0 && index != 0) {
        var show by remember {
            mutableStateOf(false)
        }
        if (Appodeal.canShow(Appodeal.MREC, "explore")) {
            Appodeal.show(context as Activity, Appodeal.MREC, "explore")
            show = true
        }
        AnimatedVisibility(visible = show) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(20.dp))
                AndroidViewBinding(
                    AdviewBinding::inflate, modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

    }
    ExpandImage(
        item = listing,
        expanded = expanded,
        onDismiss = { expanded = false },
        navigator = navigator,
        showDetails = true,
        pageState = pageState
    )
}