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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.cakkie.networkModels.ListingResponse
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.PreviewListingDestination
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun Listings(navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val listings = viewModel.listings.observeAsState(ListingResponse()).value
    Box(modifier = Modifier.fillMaxSize()) {
        if (listings.data.isEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

            }

            CakkieButton(
                text = stringResource(id = R.string.create_listing),
                modifier = Modifier
//                .fillMaxWidth(0.8f)
                    .offset(y = (-100).dp)
                    .align(Alignment.BottomCenter)
            ) {
                navigator.navigate(ChooseMediaDestination) {
                    launchSingleTop = true
                }
            }
        } else {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    Modifier
                        .background(Color.White)
                        .height(40.dp)
                        .clickable {
                            navigator.navigate(ChooseMediaDestination) {
                                launchSingleTop = true
                            }
                        }
                        .fillMaxWidth(),
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
                LazyColumn(Modifier.padding(vertical = 5.dp)) {
                    items(listings.data) { listing ->
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