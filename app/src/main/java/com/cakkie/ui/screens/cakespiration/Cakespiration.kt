package com.cakkie.ui.screens.cakespiration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.di.CakkieApp
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.utill.Toaster
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Cakespiration(
    id: String,
    item: Listing? = null,
    items: String = "",
    navigator: DestinationsNavigator
) {
    val viewModel: ExploreViewModal = koinViewModel()
    val context = LocalContext.current
    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(CakkieApp.simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }
    val cakespirationRes = viewModel.cakespiration.observeAsState().value
    val prevItems = items.toObjectList(Listing::class.java)
    val cakespirations = remember {
        mutableStateListOf(
            *prevItems.toTypedArray()
        )
    }
    val listState =
        rememberLazyListState(0)

    LaunchedEffect(key1 = cakespirationRes) {
        if (cakespirationRes?.data.isNullOrEmpty()) {
            viewModel.getCakespirations(context)
        } else {
            //add all without duplicates
            cakespirationRes?.data?.forEach { res ->
                if (cakespirations.find { it.id == res.id } == null) {
                    cakespirations.add(res)
                }
            }
        }
    }

    LaunchedEffect(key1 = id) {
        if (id.isNotEmpty()) {
            if (cakespirations.find { it.id == id } == null) {
                if (item == null) {
                    viewModel.getListing(id).addOnSuccessListener {
                        cakespirations.add(0, it)
                    }.addOnFailureListener {
                        Toaster(
                            context,
                            it.localizedMessage ?: "Something went wrong",
                            R.drawable.logo
                        )
                    }
                } else {
                    cakespirations.add(0, item)
                }
                delay(500)
                listState.scrollToItem(0)
            } else {
                listState.scrollToItem(cakespirations.indexOf(cakespirations.find { it.id == id }))
            }
        }
    }

    var isMuted by rememberSaveable { mutableStateOf(true) }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            items(items = cakespirations, key = { it.id }) { listing ->
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillParentMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CakespirationItem(
                        progressiveMediaSource = progressiveMediaSource,
                        isMuted = isMuted,
                        onMute = { isMuted = it },
                        item = listing,
                        navigator = navigator,
                        shouldPlay = remember {
                            derivedStateOf { listState.firstVisibleItemIndex }
                        }.value == cakespirations.indexOf(listing)
                    )
                }
            }
        }

        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navigator.popBackStack() },
                Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.cakespiation),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                color = CakkieBackground
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.size(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(
                        id = R.string.search
                    ),
                    tint = CakkieBackground,
                    modifier = Modifier.width(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = CakkieBackground
                )
                Spacer(modifier = Modifier.size(1.dp))
                Divider(
                    Modifier
                        .size(2.dp)
                        .clip(CircleShape), color = CakkieBackground
                )
                Spacer(modifier = Modifier.size(1.dp))
                if (cakespirations.size > 0) {
                    Text(
                        text = cakespirations[remember {
                            derivedStateOf { listState.firstVisibleItemIndex }
                        }.value].name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        color = CakkieBackground
                    )
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    tint = CakkieBackground,
                    modifier = Modifier
                        .rotate(180f)
                        .width(24.dp),
                )
            }
        }
    }
}