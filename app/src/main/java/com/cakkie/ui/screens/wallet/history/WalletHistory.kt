package com.cakkie.ui.screens.wallet.history

import androidx.compose.foundation.Image
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.Transaction
import com.cakkie.ui.screens.wallet.components.WalletFilter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WalletHistory(navigator: DestinationsNavigator) {
    var filter by remember { mutableStateOf("All") }
    val history = listOf<Transaction>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "go back", modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.transaction_history),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        WalletFilter(filter) {
            filter = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (history.isNotEmpty()) {
            LazyColumn {
                items(
                    items = history,
                ) {
                    HistoryItem(it)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "No history found",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }
}