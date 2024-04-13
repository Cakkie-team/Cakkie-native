package com.cakkie.ui.screens.explore

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.BottomState.hideNav
import com.cakkie.R
import com.cakkie.data.db.models.User
import com.cakkie.di.CakkieApp.Companion.simpleCache
import com.cakkie.ui.screens.destinations.CakespirationDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.destinations.NotificationDestination
import com.cakkie.ui.screens.destinations.ReactivateAccountDestination
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
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@androidx.annotation.OptIn(UnstableApi::class)
@Destination
@Composable
fun ExploreScreen(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val context = LocalContext.current
    val user = viewModel.user.observeAsState(User()).value
    val listings = viewModel.listings.observeAsState().value
    val cakespiration = viewModel.cakespiration.observeAsState().value
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


    LaunchedEffect(scrollFraction) {
        visible = scrollFraction <= prevScroll
        hideNav.value = scrollFraction > prevScroll
        if (scrollFraction > 20) {
            prevScroll = scrollFraction
        }

    }

    //note scroll offset


    LaunchedEffect(Unit) {
        if (listings?.data.isNullOrEmpty()) {
            viewModel.getListings(context)
        }
        if (cakespiration?.data.isNullOrEmpty()) {
            viewModel.getCakespirations(context)
        }
    }

    LaunchedEffect(listings) {
        Timber.d("Listings: ${listings?.data}")
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
                        model = user?.profileImage?.replace("http", "https") ?: "",
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
                                    items = (cakespiration?.data ?: listOf()).toString()
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
                    items(
                        items = cakespiration?.data ?: listOf(),
                    ) {
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
                                dropOff = 0.5f,
                                tilt = 20f
                            ),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            if (listings != null) {
                items(
                    items = listings.data,
                    key = { it.id }
                ) { listing ->
                    val index = listings.data.indexOf(listing)
                    val visibleItem =
                        remember { derivedStateOf { listState.firstVisibleItemIndex } }.value
                    Timber.d("index ${listing.name}: $index visibleIndex: $visibleItem")
                    ExploreItem(
//                       user = user,
                        navigator = navigator,
                        item = listing,
                        shouldPlay = remember {
                            derivedStateOf { index == visibleItem }
                        }.value,
                        isMuted = isMuted,
                        onMute = { isMuted = it },
                        progressiveMediaSource = progressiveMediaSource,
                        index = index
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(180.dp)) }
        }
    }
}