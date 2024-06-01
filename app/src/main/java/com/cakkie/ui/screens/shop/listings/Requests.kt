package com.cakkie.ui.screens.shop.listings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.Order
import com.cakkie.networkModels.OrderResponse
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.orders.components.OrdersItem
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun Requests(navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val orderRes = viewModel.orders.observeAsState(OrderResponse()).value
    val orders = remember {
        mutableStateListOf<Order>()
    }

    LaunchedEffect(key1 = orderRes.data) {
        orders.addAll(orderRes?.data?.filterNot { res ->
            orders.any { it.id == res.id }
        } ?: emptyList())
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        if (orders.isEmpty()) {
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
                    .offset(y = (-30).dp)
                    .align(Alignment.BottomCenter)
            ) {
                navigator.navigate(ChooseMediaDestination(R.string.images)) {
                    launchSingleTop = true
                }
            }
        } else {
            LazyColumn(Modifier.padding(vertical = 5.dp)) {
                items(orders, key = { it.id }) { order ->
                    val index = orders.indexOf(order)
                    if (index > orders.lastIndex - 2 && orderRes.data.isNotEmpty()) {
                        viewModel.getMyListings(orderRes.meta.nextPage, orderRes.meta.pageSize)
                    }
                    OrdersItem(order) {
                    }
                }
            }
        }
    }
}