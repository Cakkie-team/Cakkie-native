package com.cakkie.ui.screens.search

import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.di.CakkieApp.Companion.simpleCache
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.search.tabs.AllTabContent
import com.cakkie.ui.screens.search.tabs.JobsTabContent
import com.cakkie.ui.screens.search.tabs.ListingsTabContent
import com.cakkie.ui.screens.search.tabs.ShopsTabContent
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    val viewModel: SearchViewModel = koinViewModel()

    val isSearching = viewModel.isSearching.collectAsState().value
    val searchQuery = viewModel.searchQuery.collectAsState().value
    val listings = viewModel.initialListings.collectAsState().value
    val searchedListings = viewModel.searchedListings.collectAsState().value
    val searchedJobs = viewModel.searchedJobs.collectAsState().value
    val searchedShops = viewModel.searchedShops.collectAsState().value
    val searchedAllResults = viewModel.searchedAllResults.collectAsState().value

    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }

    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.loadListings()
        delay(1000)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    var prevScroll by remember { mutableIntStateOf(0) }
    val scrollFraction =
        remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }.value
    val visibleItem = remember { derivedStateOf { listState.firstVisibleItemIndex } }.value

    val scrollFractionGrid =
        remember { derivedStateOf { gridState.firstVisibleItemScrollOffset } }.value
    val visibleItemGrid = remember { derivedStateOf { gridState.firstVisibleItemIndex } }.value

    LaunchedEffect(scrollFraction) {
        if (scrollFraction > 20) {
            prevScroll = scrollFraction
        }
    }
    LaunchedEffect(scrollFractionGrid) {
        if (scrollFractionGrid > 20) {
            prevScroll = scrollFractionGrid
        }
    }

    var isScrollingFast by remember { mutableStateOf(false) }
    var lastScrollTime by remember { mutableLongStateOf(0L) }
    var lastScrollPosition by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { currentScrollPosition ->
                val currentTime = System.currentTimeMillis()
                val timeDiff = currentTime - lastScrollTime
                if (timeDiff > 0) {
                    val speed = (currentScrollPosition - lastScrollPosition).toFloat() / timeDiff
                    isScrollingFast = speed > 0.3
                }
                lastScrollPosition = currentScrollPosition
                lastScrollTime = currentTime
            }
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex }
            .collect { currentScrollPosition ->
                val currentTime = System.currentTimeMillis()
                val timeDiff = currentTime - lastScrollTime
                if (timeDiff > 0) {
                    val speed = (currentScrollPosition - lastScrollPosition).toFloat() / timeDiff
                    isScrollingFast = speed > 0.3
                }
                lastScrollPosition = currentScrollPosition
                lastScrollTime = currentTime
            }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navigator.popBackStack() }
            )

            // Search field
            CakkieInputField(
                value = searchQuery,
                onValueChange = { query ->
                    viewModel.onSearchQueryChanged(
                        query, when (pagerState.currentPage) {
                            0 -> "All"
                            1 -> "Listing"
                            2 -> "Job"
                            3 -> "Shop"
                            else -> "All"
                        }
                    )
                },
                placeholder = stringResource(id = R.string.search_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .height(55.dp),
                keyboardType = KeyboardType.Text,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (isSearching) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = CakkieBrown
            )
        } else {
            Box(
                modifier = Modifier
                    .pullRefresh(state = state)
                    .fillMaxSize()
            ) {
                if (searchQuery.isEmpty()) {
                    if (listings.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = CakkieBrown
                            )
                        }
                    } else {
                        // Show Listings on startup and display tabs once a query is entered
                        ShowListings(
                            listings,
                            searchQuery,
                            gridState,
                            visibleItemGrid,
                            isScrollingFast,
                            progressiveMediaSource,
                            navigator
                        )
                    }

                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = state,
                        modifier = Modifier.align(Alignment.TopCenter),
                        backgroundColor = CakkieBackground,
                        contentColor = CakkieBrown
                    )
                }

                // Tabs for search results
                if ((searchedListings.isNotEmpty()
                            || searchedJobs.isNotEmpty()
                            || searchedShops.isNotEmpty()
                            || searchedAllResults.isNotEmpty())
                    && searchQuery.isNotEmpty()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(Modifier.fillMaxHeight()) {
                        PageTabs(
                            pagerState = pagerState,
                            pageCount = pagerState.pageCount,
                            tabs = listOf(
                                stringResource(id = R.string.all),
                                stringResource(id = R.string.jobs),
                                stringResource(id = R.string.listings),
                                stringResource(id = R.string.shop)
                            )
                        )

                        Box(modifier = Modifier.fillMaxSize()) {
                            HorizontalPager(state = pagerState) { page ->
                                when (page) {
                                    0 -> {
                                        // All Tab
                                        AllTabContent(
                                            items = searchedAllResults,
                                            navigator = navigator,
                                            visibleItem = visibleItem,
                                            isScrollingFast = isScrollingFast,
                                            progressiveMediaSource = progressiveMediaSource,
                                            listState = listState
                                        )
                                    }

                                    // Jobs Tab
                                    1 -> {
                                        JobsTabContent(
                                            listState = listState,
                                            items = searchedJobs,
                                            navigator = navigator
                                        )
                                    }

                                    // Listings Tab
                                    2 -> {
                                        ListingsTabContent(
                                            items = searchedListings,
                                            gridState = gridState,
                                            visibleItem = visibleItemGrid,
                                            isScrollingFast = isScrollingFast,
                                            navigator = navigator,
                                            progressiveMediaSource = progressiveMediaSource
                                        )
                                    }

                                    // Shops Tab
                                    3 -> {
                                        ShopsTabContent(
                                            listState = listState,
                                            items = searchedShops,
                                            navigator = navigator
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ShowListings(
    listings: List<Listing>,
    searchQuery: String,
    gridState: LazyGridState,
    visibleItemGrid: Int,
    isScrollingFast: Boolean,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator
) {
    if (searchQuery.isEmpty()) {
        ListingsTabContent(
            items = listings,
            gridState = gridState,
            visibleItem = visibleItemGrid,
            isScrollingFast = isScrollingFast,
            navigator = navigator,
            progressiveMediaSource = progressiveMediaSource
        )
    }
}



