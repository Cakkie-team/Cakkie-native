package com.cakkie.ui.screens.wallet.earn


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Referral(navigator: DestinationsNavigator) {
    val history = listOf<String>()
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
                text = stringResource(id = R.string.your_referrals),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.your_friends),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (history.isNotEmpty()) {
            LazyColumn {
                items(
                    items = history,
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
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
                    text = "You have not invited any friend yet",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }
}