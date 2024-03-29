package com.cakkie.ui.screens.wallet.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.cakkie.ui.screens.wallet.components.WalletFilter
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WalletHistory() {
    var filter by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "", modifier = Modifier.clickable { })
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
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(
                items = (0..10).toList(),
            ) {
                HistoryItem()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}