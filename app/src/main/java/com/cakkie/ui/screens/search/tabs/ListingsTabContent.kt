package com.cakkie.ui.screens.search.tabs

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.data.db.models.Listing
import com.cakkie.ui.screens.search.SearchItem
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(UnstableApi::class)
@Composable
fun ListingsTabContent(
    items: List<Listing>,
    gridState: LazyGridState,
    visibleItem: Int,
    isScrollingFast: Boolean,
    navigator: DestinationsNavigator,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
) {
    if (items.isEmpty()) {
        NoResultContent()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(1.dp),
        ) {
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