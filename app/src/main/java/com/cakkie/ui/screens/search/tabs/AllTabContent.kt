package com.cakkie.ui.screens.search.tabs

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.jobs.AllTabJobItem
import com.cakkie.ui.screens.search.SearchItem
import com.cakkie.ui.screens.shop.AllTabShopItem
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(UnstableApi::class)
@Composable
fun AllTabContent(
    items: List<Any>,
    visibleItem: Int,
    isScrollingFast: Boolean,
    listState: LazyListState,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator
) {

    val listings = items.filterIsInstance<Listing>()
    val jobs = items.filterIsInstance<JobModel>()
    val shops = items.filterIsInstance<ShopModel>()

    val gridSize = minOf(9, listings.size)
    val firstNineListings = listings.take(gridSize)
    val remainingListings = listings.drop(gridSize)

    val firstGridState = rememberLazyGridState()
    val secondGridState = rememberLazyGridState()
    val jobsRowState = rememberLazyListState()
    val shopsRowState = rememberLazyListState()

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemWidth = screenWidth * 0.8f

    if (items.isEmpty()) {
        NoResultContent()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            state = listState
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Listings Section - First 9 Listings
            if (firstNineListings.isNotEmpty()) {
                item {
                    LazyVerticalGrid(
                        state = firstGridState,
                        columns = GridCells.Adaptive(minSize = 120.dp),
                        modifier = Modifier.fillMaxWidth().heightIn(max = 1000.dp),
                        contentPadding = PaddingValues(1.dp),
                    ) {
                        items(firstNineListings) { listing ->
                            val index = firstNineListings.indexOf(listing)
                            SearchItem(
                                listing = listing,
                                navigator = navigator,
                                shouldPlay = index == visibleItem && !isScrollingFast,
                                progressiveMediaSource = progressiveMediaSource,
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Jobs Section
            if (jobs.isNotEmpty()) {
                item {
                    LazyRow(
                        state = jobsRowState,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(jobs) { job ->
                            Box(
                                modifier = Modifier
                                    .width(itemWidth)
                            ) {
                                AllTabJobItem(
                                    item = job,
                                    onClick = {
                                        navigator.navigate(JobDetailsDestination(job.id, job))
                                    },
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Shops Section
            if (shops.isNotEmpty()) {
                item {
                    LazyRow(
                        state = shopsRowState,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(shops) { shop ->
                            Box(
                                modifier = Modifier
                                    .width(itemWidth)
                            ) {
                                AllTabShopItem(
                                    item = shop,
                                    onClick = {},
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Remaining Listings Section
            if (remainingListings.isNotEmpty()) {
                item {
                    LazyVerticalGrid(
                        state = secondGridState,
                        columns = GridCells.Adaptive(minSize = 120.dp),
                        modifier = Modifier.fillMaxWidth().heightIn(max = 1000.dp),
                        contentPadding = PaddingValues(1.dp),
                    ) {
                        items(remainingListings) { listing ->
                            val index = remainingListings.indexOf(listing)
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
        }
    }
}
