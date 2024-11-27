package com.cakkie.ui.screens.shop.listings

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.User
import com.cakkie.di.CakkieApp
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.DeleteListingDestination
import com.cakkie.ui.screens.destinations.SetAvailabilityDestination
import com.cakkie.ui.screens.explore.ExploreItem
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.cakkie.utill.ContentType
import com.cakkie.utill.ShareUtil
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@Destination
@Composable
fun PreviewListing(
    id: String,
    item: Listing = Listing(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SetAvailabilityDestination, Listing>
) {
    val context = LocalContext.current
    val viewModel: ShopViewModel = koinViewModel()
    val user = viewModel.user.observeAsState(User()).value
    var listing by rememberSaveable {
        mutableStateOf(item)
    }
    var isMuted by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(key1 = id) {
        viewModel.getListing(id).addOnSuccessListener {
            listing = it
        }.addOnFailureListener {
            Toaster(context, it.localizedMessage ?: "Something went wrong", R.drawable.logo)
        }
    }

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                listing = result.value
            }
        }
    }
    val progressiveMediaSource = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(CakkieApp.simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
//        Spacer(modifier = Modifier.height(20.dp))
        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }

            Text(
                stringResource(id = R.string.shop),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(id = R.string.preview),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CakkieBrown,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        ShareUtil.share(
                            contentType = ContentType.Listing,
                            context = context,
                            contentId = id
                        )
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "share",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                    IconButton(onClick = {
                        navigator.navigate(DeleteListingDestination(item = listing))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "share",
                            modifier = Modifier
                                .size(24.dp),
                            tint = Error
                        )
                    }
                }
            }

            ExploreItem(
                item = listing,
                navigator = navigator,
                shouldPlay = true,
                isMuted = isMuted,
                onMute = { isMuted = it },
                progressiveMediaSource = progressiveMediaSource
            )
            Spacer(modifier = Modifier.height(10.dp))
            CakkieButton(
                text = stringResource(id = R.string.set_availability), modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                navigator.navigate(SetAvailabilityDestination(item = listing))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(id = R.string.view_comments),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CakkieBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clickable { navigator.navigate(CommentDestination(item)) }
            )
            Spacer(modifier = Modifier.height(80.dp))
        }


    }
}