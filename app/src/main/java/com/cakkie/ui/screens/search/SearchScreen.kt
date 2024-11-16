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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
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
import com.cakkie.ui.screens.search.tabs.NoResultContent
import com.cakkie.ui.screens.search.tabs.ShopsTabContent
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    val viewModel: SearchViewModel = koinViewModel()

    val isSearching = viewModel.isSearching.collectAsState()
    val isLoading = viewModel.isLoading.observeAsState(false).value
    val searchQuery = viewModel.searchQuery.collectAsState()
    val filteredListings = viewModel.filteredListings.collectAsState()
    val searchedListings = viewModel.searchedListing.collectAsState()
    val filteredJobs = viewModel.filteredJobs.collectAsState()
    val filteredShops = viewModel.filteredShops.collectAsState()
    val filteredAll = viewModel.filteredAll.collectAsState()

    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }

    // val config = LocalConfiguration.current
    // val height = config.screenHeightDp.dp

    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()
    val pagerState = rememberPagerState(pageCount = { 4 })
    // val refreshScope = rememberCoroutineScope()
    // var refreshing by remember { mutableStateOf(false) }

//    fun refresh() = refreshScope.launch {
//        refreshing = true
//        viewModel.loadListings()
//        viewModel.loadJobs()
//        viewModel.loadShops()
//        delay(1000)
//        refreshing = false
//    }

    // val state = rememberPullRefreshState(refreshing, ::refresh)

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

//    LaunchedEffect(Unit) {
//        if (filteredListings.value.isEmpty()) {
//            viewModel.loadListings()
//        }
//        if (filteredJobs.value.isEmpty()) {
//            viewModel.loadJobs()
//        }
//        if (filteredShops.value.isEmpty()) {
//            viewModel.loadShops()
//        }
//        if (filteredAll.value.isEmpty()) {
//            viewModel.loadListings()
//            viewModel.loadJobs()
//            viewModel.loadShops()
//        }
//    }

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
                value = searchQuery.value,
                onValueChange = { query ->
                    viewModel.onSearchQueryChanged(
                        query, when (pagerState.currentPage) {
                            0 -> "all"
                            1 -> "listing"
                            2 -> "job"
                            3 -> "shop"
                            else -> "all"
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

        if (isSearching.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                color = CakkieBrown
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (searchQuery.value.isEmpty()) {
                    if (filteredListings.value.isEmpty()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            color = CakkieBrown
                        )
                    } else {
                        // Show Listings on startup and display tabs once a query is entered
                        ShowListings(
                            filteredListings,
                            searchQuery,
                            gridState,
                            visibleItemGrid,
                            isScrollingFast,
                            progressiveMediaSource,
                            navigator
                        )
                    }
                }

//                if (searchQuery.value.isNotEmpty() && searchedListings.value.isEmpty()
//                    && filteredJobs.value.isEmpty() && filteredShops.value.isEmpty()
//                    && filteredAll.value.isEmpty()
//                ) {
//                    NoResultContent()
//                }
                Log.d("SearchScreen", "Search query: ${searchQuery.value}")
                Log.d("SearchScreen", "filteredAll size: ${filteredAll.value.size}")
                Log.d("SearchScreen", "filteredListings size: ${filteredListings.value.size}")
                Log.d("SearchScreen", "filteredJobs size: ${filteredJobs.value.size}")
                Log.d("SearchScreen", "filteredShops size: ${filteredShops.value.size}")

                // Tabs for search results
                if ((searchedListings.value.isNotEmpty()
                            || filteredJobs.value.isNotEmpty()
                            || filteredShops.value.isNotEmpty()
                            || filteredAll.value.isNotEmpty())
                    && searchQuery.value.isNotEmpty()
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
                                        if ((searchedListings.value.isNotEmpty() || filteredJobs.value.isNotEmpty() || filteredShops.value.isNotEmpty() || filteredAll.value.isNotEmpty())
                                            && searchQuery.value.isNotEmpty()
                                            && !isSearching.value
                                        ) {
                                            AllTabContent(
                                                items = filteredAll.value,
                                                navigator = navigator,
                                                visibleItem = visibleItem,
                                                isScrollingFast = isScrollingFast,
                                                progressiveMediaSource = progressiveMediaSource,
                                                listState = listState
                                            )
                                        } else {
                                            NoResultContent()
                                        }
                                    }

                                    1 -> {
                                        // Jobs Tab
                                        if (filteredJobs.value.isNotEmpty() && searchQuery.value.isNotEmpty() && !isSearching.value) {
                                            JobsTabContent(
                                                listState = listState,
                                                items = filteredJobs.value,
                                                navigator = navigator
                                            )
                                        } else {
                                            NoResultContent()
                                        }
                                    }

                                    2 -> {
                                        // Listings Tab
                                        if (searchedListings.value.isNotEmpty()
                                            && searchQuery.value.isNotEmpty()
                                            && !isSearching.value
                                        ) {
                                            ListingsTabContent(
                                                items = searchedListings.value,
                                                gridState = gridState,
                                                visibleItem = visibleItemGrid,
                                                isScrollingFast = isScrollingFast,
                                                navigator = navigator,
                                                progressiveMediaSource = progressiveMediaSource
                                            )
                                        } else {
                                            NoResultContent()
                                        }
                                    }

                                    3 -> {
                                        // Shops Tab
                                        if (filteredShops.value.isNotEmpty() && searchQuery.value.isNotEmpty() && !isSearching.value) {
                                            ShopsTabContent(
                                                listState = listState,
                                                items = filteredShops.value,
                                                navigator = navigator
                                            )
                                        } else {
                                            NoResultContent()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//                PullRefreshIndicator(
//                    refreshing = refreshing,
//                    state = state,
//                    modifier = Modifier.align(Alignment.TopCenter),
//                    backgroundColor = CakkieBackground,
//                    contentColor = CakkieBrown
//                )

            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ShowListings(
    filteredListings: State<List<Listing>>,
    searchQuery: State<String>,
    gridState: LazyGridState,
    visibleItemGrid: Int,
    isScrollingFast: Boolean,
    progressiveMediaSource: ProgressiveMediaSource.Factory,
    navigator: DestinationsNavigator
) {
    if (filteredListings.value.isNotEmpty() && searchQuery.value.isEmpty()) {
        ListingsTabContent(
            items = filteredListings.value,
            gridState = gridState,
            visibleItem = visibleItemGrid,
            isScrollingFast = isScrollingFast,
            navigator = navigator,
            progressiveMediaSource = progressiveMediaSource
        )
    }
}



