package com.cakkie.ui.screens.search.tabs

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.jobs.JobsItems
import com.cakkie.ui.screens.search.SearchItem
import com.cakkie.ui.screens.shop.ShopItem
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
    val listingRowState = rememberLazyListState()
    val jobsRowState = rememberLazyListState()
    val shopsRowState = rememberLazyListState()

    val listings = remember { items.filterIsInstance<Listing>() }
    val jobs = remember { items.filterIsInstance<JobModel>() }
    val shops = remember { items.filterIsInstance<ShopModel>() }

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

            // Listings Section
            if (listings.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.listings),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item {
                    LazyRow(
                        state = listingRowState,
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(listings.chunked(3)) { columnItems ->
                            Column(modifier = Modifier.safeContentPadding()) {
                                for (listing in columnItems) {
                                    Box(modifier = Modifier.size(120.dp)) {
                                        val index = items.indexOf(listings)
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
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Jobs Section
            if (jobs.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.jobs),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
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
                                JobsItems(
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
                    Text(
                        text = stringResource(id = R.string.shops),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
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
                                ShopItem(
                                    item = shop,
                                    onClick = {},
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}