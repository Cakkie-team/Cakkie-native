package com.cakkie.ui.screens.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.networkModels.Order
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.screens.destinations.OrderDetailsDestination
import com.cakkie.ui.screens.orders.components.OrdersItem
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun Orders(navigator: DestinationsNavigator) {
    val viewModel: OrderViewModel = koinViewModel()
    val orderRes = viewModel.orders.observeAsState().value
    val orders = remember {
        mutableStateListOf<Order>()
    }
    var filter by remember { mutableStateOf("All") }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getMyOrders(status = if (filter == "All") null else filter)
        delay(1000)
        refreshing = false
    }
    LaunchedEffect(key1 = filter) {
        refresh()
    }
    LaunchedEffect(key1 = orderRes?.data) {
        if (orderRes?.meta?.currentPage == 0) orders.clear()
        orders.addAll(orderRes?.data?.filterNot { res ->
            orders.any { it.id == res.id }
        } ?: emptyList())
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .pullRefresh(state = state)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
            ) {
                Image(
                    painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                        .clickable {
                            navigator.popBackStack()
                        },
                )
                Text(
                    text = stringResource(id = R.string.orders),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            CakkieFilter(
                filter,
                options = listOf(
                    "All",
                    "Pending",
                    "Inprogress",
                    "Approved",
                    "Cancelled",
                    "Delivered",
                    "Released",
                    "Ready",
                    "Accepted",
                    "ArrivedShop",
                    "Shipping",
                    "Arrived",
                    "Completed",
                    "Declined"
                )
            ) {
                filter = it
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (orders.isEmpty()) {
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                Icon(
                    painter = painterResource(id = R.drawable.orders),
                    contentDescription = "orders",
                    tint = CakkieLightBrown,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.no_orders),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                )
            }
            LazyColumn {
                items(items = orders) {
                    val index = orders.indexOf(it)
                    if (index > orders.lastIndex - 2 && orderRes?.meta?.nextPage != null) {
                        viewModel.getMyOrders(
                            orderRes.meta.nextPage,
                            orderRes.meta.pageSize,
                            if (filter == "All") null else filter
                        )
                    }
                    OrdersItem(it) {
                        navigator.navigate(OrderDetailsDestination(it))
                    }
                }
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