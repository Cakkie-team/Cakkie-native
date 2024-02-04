package com.cakkie.ui.screens.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.R
import com.cakkie.di.CakkieApp
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatDate
import com.cakkie.utill.isVideoUrl
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(
    ExperimentalFoundationApi::class
)
@Destination
@Composable
fun ItemDetails(id: String, item: Listing = Listing(), navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val context = LocalContext.current
    var listing by rememberSaveable {
        mutableStateOf(item)
    }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var isMuted by rememberSaveable { mutableStateOf(true) }
    val imagePageState =
        rememberPagerState(pageCount = { if (listing.media.isEmpty()) 1 else listing.media.size })
    val pageState = rememberPagerState(pageCount = { 2 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val httpDataSourceFactory = remember {
        DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
    }
    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(CakkieApp.simpleCache)
        .setUpstreamDataSourceFactory(httpDataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    val progressiveMediaSource = remember {
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }


    LaunchedEffect(key1 = id) {
        viewModel.getListing(id).addOnSuccessListener {
            listing = it
        }.addOnFailureListener {
            Toaster(context, it.localizedMessage ?: "Something went wrong", R.drawable.logo)
        }
    }
    Column {
        IconButton(onClick = { navigator.popBackStack() }) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back", contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(24.dp)
            )
        }
        Column(Modifier.verticalScroll(rememberScrollState())) {
            HorizontalPager(state = imagePageState) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
//                    .clickable { expanded = !expanded }
//                    .background(Color.Black.copy(alpha = 0.6f))
                ) {
                    GlideImage(
                        imageModel = listing.media[it],
                        contentDescription = "cake",
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .fillMaxWidth()
                            .height(screenWidth)
                            .then(
                                if (item.media[it].isVideoUrl()) Modifier.blur(10.dp) else Modifier
                            ),
                        contentScale = ContentScale.Crop,
                        shimmerParams = ShimmerParams(
                            baseColor = CakkieBrown.copy(0.4f),
                            highlightColor = CakkieBrown.copy(0.8f),
                            dropOff = 0.55f,
                            tilt = 20f
                        )
                    )
                    if (item.media[it].isVideoUrl()) {
//                        val exoPlayer = remember {
//                            ExoPlayer.Builder(context)
//                                .build()
//                                .apply {
//
//                                    val source = progressiveMediaSource
//                                        .createMediaSource(MediaItem.fromUri(item.media[it]))
//                                    setMediaSource(source)
//                                    prepare()
//                                }
//                        }

                        val exoPlayer = remember { ExoPlayer.Builder(context).build() }
                        val source = progressiveMediaSource
                            .createMediaSource(MediaItem.fromUri(item.media[it]))
                        exoPlayer.setMediaSource(source)
                        if (!expanded) {
                            exoPlayer.pause()
                        } else {
                            exoPlayer.play()
                        }
                        VideoPlayer(
                            exoPlayer = exoPlayer,
                            isPlaying = !expanded,
                            mute = isMuted,
                            onMute = { isMuted = it },
                            modifier = Modifier
                                .heightIn(max = screenWidth)
                                .clickable {
                                    navigator.navigate(
                                        CakespirationDestination(id = item.id, item = item)
                                    )
                                }
                        )
                    }
                }
            }

            Row(
                Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalPagerIndicator(
                    pagerState = imagePageState,
                    activeColor = CakkieBrown,
                    spacing = 8.dp,
                    indicatorWidth = 5.dp,
                    indicatorHeight = 5.dp,
                    pageCount = imagePageState.pageCount,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = listing.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                GlideImage(
                    imageModel = listing.shop.image,
                    contentDescription = "shop logo",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(ProfileDestination)
                        }, shimmerParams = ShimmerParams(
                        baseColor = CakkieBrown.copy(0.4f),
                        highlightColor = CakkieBrown.copy(0.8f),
                        dropOff = 0.55f,
                        tilt = 20f
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = listing.shop.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = listing.createdAt.formatDate(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            //page tabs
            PageTabs(
                pagerState = pageState, pageCount = pageState.pageCount,
                tabs = listOf(
                    stringResource(id = R.string.description),
                    stringResource(id = R.string.reviews)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            //page
            HorizontalPager(state = pageState) {
                when (it) {
                    0 -> Description(listing, navigator)
                    1 -> Reviews()
                }
            }
        }
    }


    ExpandImage(
        item = listing,
        expanded = expanded,
        onDismiss = { expanded = false },
        navigator = navigator,
        pageState = imagePageState
    )
}