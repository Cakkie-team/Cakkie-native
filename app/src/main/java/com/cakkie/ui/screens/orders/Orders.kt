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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.screens.destinations.OrderDetailsDestination
import com.cakkie.ui.screens.orders.components.OrdersItem
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Orders(navigator: DestinationsNavigator) {
    val orders = emptyList<Int>()
    var filter by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier
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
        CakkieFilter(filter) {
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
                OrdersItem(it) {
                    navigator.navigate(OrderDetailsDestination(it))
                }
            }
        }
    }
}