package com.cakkie.ui.screens.explore

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.BottomState.hideNav
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.di.CakkieApp.Companion.simpleCache
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.destinations.NotificationDestination
import com.cakkie.ui.screens.destinations.ReactivateAccountDestination
import com.cakkie.ui.screens.destinations.SearchScreenDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.WalletDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@androidx.annotation.OptIn(UnstableApi::class)
@Destination
@Composable
fun ExploreScreen(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val context = LocalContext.current
    val user = viewModel.user.observeAsState().value
    val listings = viewModel.listings.observeAsState().value
    val cakespiration = viewModel.cakespiration.observeAsState().value
    val post = remember {
        mutableStateListOf<Listing>()
    }
    val story = remember {
        mutableStateListOf<Listing>()
    }
    val listState = rememberLazyListState()
    var isMuted by rememberSaveable { mutableStateOf(true) }
    var visible by remember { mutableStateOf(true) }

    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }
    var prevScroll by remember { mutableIntStateOf(0) }
    val scrollFraction =
        remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }.value

    val visibleItem = remember { derivedStateOf { listState.firstVisibleItemIndex } }.value
    LaunchedEffect(scrollFraction) {
        visible = scrollFraction <= prevScroll
        hideNav.value = scrollFraction > prevScroll
        if (scrollFraction > 20) {
            prevScroll = scrollFraction
        }

    }



    LaunchedEffect(Unit) {
        if (listings?.data.isNullOrEmpty()) {
            viewModel.getListings(context)
        }
        if (cakespiration?.data.isNullOrEmpty()) {
            viewModel.getCakespirations(context)
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getListings(context)
        viewModel.getCakespirations(context)
        delay(1000)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(listings?.data) {
        if (listings?.meta?.currentPage == 0) {
            post.clear()
        }
        post.addAll(listings?.data?.filterNot { res ->
            post.any { it.id == res.id }
        } ?: emptyList())
    }

    LaunchedEffect(cakespiration?.data) {
        if (cakespiration?.meta?.currentPage == 0) {
            story.clear()
        }
        story.addAll(cakespiration?.data?.filterNot { res ->
            story.any { it.id == res.id }
        } ?: emptyList())
    }

    //check if user is has a shop
    LaunchedEffect(key1 = user) {
        if (user?.isDeleted == true) {
            //navigate to create shop screen
            navigator.navigate(ReactivateAccountDestination) {
                popUpTo(ExploreScreenDestination) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    //note scroll offset
    var isScrollingFast by remember { mutableStateOf(false) }
//    var isScrolling by remember { mutableStateOf(false) }
    var lastScrollTime by remember { mutableLongStateOf(0L) }
    var lastScrollPosition by remember { mutableIntStateOf(0) }
    // Update visibleItem when scrolling stops
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.isScrollInProgress }
//            .distinctUntilChanged()
//            .collect { scrolling ->
//                isScrolling = scrolling
//                if (!scrolling) {
//                    // Scrolling has stopped, update visibleItem
////                    val firstVisibleItem = lazyListState.firstVisibleItemIndex
////                    val firstVisibleItemOffset = lazyListState.firstVisibleItemScrollOffset
////                    visibleItem = if (firstVisibleItemOffset == 0) {
////                        firstVisibleItem
////                    } else {
////                        firstVisibleItem + 1
////                    }
//                }
//            }
//    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { currentScrollPosition ->
                val currentTime = System.currentTimeMillis()
                val timeDiff = currentTime - lastScrollTime
                if (timeDiff > 0) {
                    val speed = (currentScrollPosition - lastScrollPosition).toFloat() / timeDiff
                    isScrollingFast = speed > 0.3 // Adjust the threshold as needed
                }
                lastScrollPosition = currentScrollPosition
                lastScrollTime = currentTime
            }
    }

    Box(
        modifier = Modifier
            .pullRefresh(state = state)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = visible) {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
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
                            model = user?.profileImage?.replace(Regex("\\bhttp://"), "https://")
                                ?: "",
                            contentDescription = "profile pic",
                            onState = {
                                //update isLoaded
                                isLoading = it is AsyncImagePainter.State.Loading
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    navigator.navigate(MyProfileDestination)
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
                                text = "Welcome \uD83D\uDC4B",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = user?.name ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navigator.navigate(SearchScreenDestination) }) {
                            Image(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "search",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = { navigator.navigate(NotificationDestination) }) {
                            Image(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = "notification",
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        IconButton(onClick = { navigator.navigate(WalletDestination) }) {
                            Image(
                                painter = painterResource(id = R.drawable.wallet),
                                contentDescription = "wallet",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            LazyColumn(state = listState) {
                item {
                    Row(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.cakespiration),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navigator.navigate(
                                    CakespirationDestination(
                                        "",
                                        items = (story).toString()
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.watch_all),
                                style = MaterialTheme.typography.bodyLarge,
                                color = CakkieBrown
                            )
                            Image(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "arrow back",
                                modifier = Modifier
                                    .rotate(180f)
                                    .width(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        Modifier.padding(horizontal = 16.dp)
                    ) {
                        item {
                            var isLoading by remember {
                                mutableStateOf(false)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(108.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = "https://cdn.cakkie.com/imgs/cake.jpg",
                                    contentDescription = "cake",
                                    onState = {
                                        //update isLoaded
                                        isLoading = it is AsyncImagePainter.State.Loading
                                    },
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(108.dp)
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .border(1.dp, CakkieBrown, RoundedCornerShape(8.dp))
                                        .clickable {
                                            if (user?.hasShop == true) {
                                                navigator.navigate(ChooseMediaDestination(R.string.videos)) {
                                                    launchSingleTop = true
                                                }
                                            } else {
                                                //navigate to create shop screen
                                                navigator.navigate(ShopDestination) {
                                                    launchSingleTop = true
                                                }

                                            }
                                        }
                                        .placeholder(
                                            visible = isLoading,
                                            highlight = PlaceholderHighlight.shimmer(),
                                            color = CakkieBrown.copy(0.8f)
                                        )
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop,
                                )

                                Box(
                                    Modifier
                                        .background(
                                            CakkieBrown.copy(0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .fillMaxSize(), contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "+",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = CakkieBrown,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 24.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clip(CircleShape)
                                                .background(CakkieBackground, CircleShape),
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.your_cakespiration),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = CakkieBackground,
                                            fontSize = 10.sp,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 12.sp
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        items(
                            items = story,
                            key = { it.id }
                        ) {
                            val index = story.indexOf(it)
                            if (index > story.lastIndex - 2 && cakespiration?.data?.isNotEmpty() == true) {
                                viewModel.getCakespirations(
                                    context,
                                    cakespiration.meta.nextPage,
                                    cakespiration.meta.pageSize
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            GlideImage(
                                imageModel = it.media.first(),
                                contentDescription = "cake",
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(108.dp)
//                                .padding(4.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .border(1.dp, CakkieBrown, RoundedCornerShape(8.dp))
                                    .clickable {
                                        navigator.navigate(
                                            CakespirationDestination(
                                                id = it.id,
                                                item = it
                                            )
                                        )
                                    },
                                contentScale = ContentScale.FillBounds,
                                shimmerParams = ShimmerParams(
                                    baseColor = CakkieBrown.copy(0.8f),
                                    highlightColor = CakkieBackground,
                                    durationMillis = 1000,
                                    dropOff = 0.4f,
                                    tilt = 20f
                                ),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }

                items(
                    items = post,
                    key = { it.id }
                ) { listing ->
                    val index = post.indexOf(listing)
                    if (index > post.lastIndex - 2 && listings?.data?.isNotEmpty() == true) {
                        viewModel.getListings(
                            context,
                            listings.meta.nextPage,
                            listings.meta.pageSize
                        )
                    }
//                    Timber.d("index ${listing.name}: $index visibleIndex: $visibleItem")
                    ExploreItem(
//                       user = user,
                        navigator = navigator,
                        item = listing,
                        shouldPlay = index == visibleItem && !isScrollingFast,
                        isMuted = isMuted,
                        onMute = { isMuted = it },
                        progressiveMediaSource = progressiveMediaSource,
//                        index = index,
                        viewModal = viewModel
                    )
                }
                item { Spacer(modifier = Modifier.height(180.dp)) }
            }
        }
        PullRefreshIndicator(
            refreshing, state,
            Modifier.align(Alignment.TopCenter),
            backgroundColor = CakkieBackground,
            contentColor = CakkieBrown
        )
    }

}