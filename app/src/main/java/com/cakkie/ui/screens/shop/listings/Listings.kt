package com.cakkie.ui.screens.shop.listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ListingResponse
import com.cakkie.data.db.models.ShopModel
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.PreviewListingDestination
import com.cakkie.ui.screens.destinations.UpgradeShopDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun Listings(
    shop: ShopModel,
    listings: ListingResponse, post: SnapshotStateList<Listing>,
    navigator: DestinationsNavigator, onLoadMore: () -> Unit
) {

    LaunchedEffect(key1 = listings.data) {
        if (listings.meta.currentPage == 0) {
            post.clear()
        }
        post.addAll(listings.data.filterNot { res ->
            post.any { it.id == res.id }
        })
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (post.isEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CakkieBrown,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.start_now_by_posting_a_listing_to_earn_requests),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColorInactive,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                CakkieButton(
                    text = stringResource(id = R.string.create_listing),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    navigator.navigate(ChooseMediaDestination(R.string.images)) {
                        launchSingleTop = true
                    }
                }
            }


        } else {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        Modifier
                            .background(Color.White)
                            .height(40.dp)
                            .clickable {
                                navigator.navigate(ChooseMediaDestination(R.string.images)) {
                                    launchSingleTop = true
                                }
                            }
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ph_plus),
                            contentDescription = "Plus",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.add_listing),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        Modifier
                            .background(Color.White)
                            .height(40.dp)
                            .clickable {
                                if (shop.isPremium) {
                                    navigator.navigate(ChooseMediaDestination(R.string.videos)) {
                                        launchSingleTop = true
                                    }
                                } else {
                                    navigator.navigate(UpgradeShopDestination) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ph_plus),
                            contentDescription = "Plus",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.add_cakespiration),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                LazyColumn(Modifier.padding(vertical = 5.dp)) {
                    items(post, key = { it.id }) { listing ->
                        val index = post.indexOf(listing)
                        if (index > post.lastIndex - 2 && listings.data.isNotEmpty()) {
                            onLoadMore.invoke()
                        }
                        ListingItem(item = listing) {
                            navigator.navigate(
                                PreviewListingDestination(
                                    id = listing.id,
                                    item = listing
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}