package com.cakkie.ui.screens.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.wallet.history.HistoryItem
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AssetDetails(navigator: DestinationsNavigator) {
    val history = (0..10).toList()
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .background(TextColorDark)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back_fill),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(20.dp)
                    .clickable {
                        navigator.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "ICING",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                color = CakkieBackground,
                fontSize = 18.sp
            )
        }
        LazyColumn {
            item {
                Box(Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.wallet_bg),
                        contentDescription = "wallet background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(165.dp),
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.log),
                            contentDescription = "asset logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .background(CakkieBackground, CircleShape)
                                .clip(CircleShape)
                                .size(40.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "200,000 ICING",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(
                                modifier = Modifier
                                    .clickable { }
                                    .width(90.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(CakkieBackground, RoundedCornerShape(8.dp))
                                        .clip(RoundedCornerShape(8.dp))
                                        .size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.deposit),
                                        contentDescription = "Deposit",
                                        modifier = Modifier.size(24.dp),
                                        tint = CakkieBrown
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Deposit",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBackground,
                                    fontSize = 16.sp
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .clickable { }
                                    .width(90.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(CakkieBackground, RoundedCornerShape(8.dp))
                                        .clip(RoundedCornerShape(8.dp))
                                        .size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.buy),
                                        contentDescription = "Buy Icing",
                                        modifier = Modifier.size(24.dp),
                                        tint = CakkieBrown
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Buy Icing",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBackground,
                                    fontSize = 16.sp
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .clickable { }
                                    .width(90.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(CakkieBackground, RoundedCornerShape(8.dp))
                                        .clip(RoundedCornerShape(8.dp))
                                        .size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.withdraw),
                                        contentDescription = "Withdraw",
                                        modifier = Modifier.size(24.dp),
                                        tint = CakkieBrown
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Withdraw",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBackground,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.history),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp),
                    color = TextColorDark,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = history
            ) {
                HistoryItem()
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (history.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
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
    }
}