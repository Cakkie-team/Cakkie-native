package com.cakkie.ui.screens.search

import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.cakkie.data.db.models.ShopModel
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.jobs.JobsItems
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
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

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
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
        } else if (mediaUrl != null) {
            var isLoading by remember { mutableStateOf(false) }

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

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
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
@kotlin.OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllTabContent(
    items: List<Any>,
    visibleItem: Int,
    isScrollingFast: Boolean,
    listState: LazyListState,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator
) {

    val listings = remember { items.filterIsInstance<Listing>() }
    val jobs = remember { items.filterIsInstance<JobModel>() }
    val shops = remember { items.filterIsInstance<ShopModel>() }

    if (items.isEmpty()) {
        NoResultsFound()
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), state = listState) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Listings Section
            if (listings.isNotEmpty()) {
                item { Text(text = stringResource(id = R.string.listings), style = MaterialTheme.typography.bodyMedium) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    val columns = if (screenWidth > 600.dp) 4 else 3

                    // FlowRow grid for Listings
                    FlowRow(
                        modifier = Modifier
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        maxItemsInEachRow = columns
                    ) {
                        val itemModifier = Modifier
                            .padding(1.dp)
                            .height(120.dp)
                            .width(120.dp)
                            .aspectRatio(1f)
                            .background(CakkieBrown)

                        listings.forEachIndexed { index, listing ->
                            Box(modifier = itemModifier) {
                                SearchItem(
                                    listing = listing,
                                    navigator = navigator,
                                    shouldPlay = index == visibleItem && !isScrollingFast,
                                    progressiveMediaSource = progressiveMediaSource
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Jobs Section
            if (jobs.isNotEmpty()) {
                item { Text(text = stringResource(id = R.string.jobs), style = MaterialTheme.typography.bodyMedium) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(jobs) { job ->
                    JobsItems(item = job, onClick = {
                        navigator.navigate(JobDetailsDestination(job.id, job))
                    })
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Shops Section
            if (shops.isNotEmpty()) {
                item { Text(text = stringResource(id = R.string.shop), style = MaterialTheme.typography.bodyMedium) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(shops) { shop ->
                    // ShopItem(item = shop, onClick = {})
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ListingTabContent(
    items: List<Listing>,
    gridState: LazyGridState,
    visibleItem: Int,
    isScrollingFast: Boolean,
    navigator: DestinationsNavigator,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
) {
    if (items.isEmpty()) {
        NoResultsFound()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            state = gridState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentPadding = PaddingValues(4.dp),
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(items) { listing ->
                val index = items.indexOf(listing)
                SearchItem(
                    listing = listing,
                    navigator = navigator,
                    shouldPlay = index == visibleItem && !isScrollingFast,
                    progressiveMediaSource = progressiveMediaSource,
                )
            }
        }
    }
}

@Composable
fun JobTabContent(items: List<JobModel>, listState: LazyListState, navigator: DestinationsNavigator) {
    if (items.isEmpty()) {
        NoResultsFound()
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(items) { job ->
                JobsItems(item = job, onClick = {
                    navigator.navigate(JobDetailsDestination(job.id, job))
                })
            }
        }
    }
}


@Composable
fun ShopTabContent(items: List<ShopModel>, listState: LazyListState, navigator: DestinationsNavigator) {
    if (items.isEmpty()) {
        NoResultsFound()
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(items) { item ->
                // ShopItem(item = item, onClick = {})
            }
        }
    }
}

@Composable
fun NoResultsFound() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "No results icon",
            tint = CakkieLightBrown,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.no_results_found),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}


