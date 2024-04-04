package com.cakkie.ui.screens.wallet

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.R
import com.cakkie.ui.screens.destinations.AssetDetailsDestination
import com.cakkie.ui.screens.destinations.DepositDestination
import com.cakkie.ui.screens.destinations.EarnDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.destinations.WalletHistoryDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

@Destination
@Composable
fun Wallet(navigator: DestinationsNavigator) {
    val viewModel: WalletViewModel = koinViewModel()
    val balance = viewModel.balance.observeAsState(listOf()).value
    val dec = DecimalFormat("#,##0.00")
    LaunchedEffect(key1 = Unit) {
        viewModel.getBalance()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(vertical = 32.dp)

    ) {
        Box(
            modifier = Modifier
                .background(TextColorDark)
//                .height(40.dp)
                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
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
                text = stringResource(id = R.string.wallet),
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
                            .height(160.dp),
                    )
                    IconButton(modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.TopEnd),
                        onClick = { navigator.navigate(WalletHistoryDestination) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.recent),
                            contentDescription = "recent",
                            modifier = Modifier.size(24.dp),
                            tint = CakkieBackground
                        )
                    }
                    IconButton(modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.TopStart),
                        onClick = { navigator.navigate(EarnDestination) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.earn),
                            contentDescription = "earnings",
                            modifier = Modifier.size(24.dp),
                            tint = CakkieBackground
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total Balance",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 16.sp
                        )
                        Text(
                            text = dec.format(
                                balance.find { it.symbol == "NGN" }?.balance ?: 0.0
                            ) + " NGN",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(
                                modifier = Modifier
                                    .clickable { navigator.navigate(DepositDestination) }
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
                                        contentDescription = "Withdrawal",
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
                    text = stringResource(id = R.string.assets),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp),
                    color = TextColorDark,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = balance
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navigator.navigate((AssetDetailsDestination(it))) }
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(65.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    var isLoading by remember {
                        mutableStateOf(false)
                    }
                    AsyncImage(
                        model = it.icon,
                        contentDescription = "currency icon",
                        modifier = Modifier
                            .background(CakkieBackground, CircleShape)
                            .size(35.dp)
                            .padding(5.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                navigator.navigate(MyProfileDestination)
                            }
                            .placeholder(
                                visible = isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = CakkieBrown.copy(0.8f)
                            ),
                        onState = {
                            //update isLoaded
                            isLoading = it is AsyncImagePainter.State.Loading
                        },
                        contentScale = ContentScale.Fit,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "₦" + dec.format(it.exchangeRate),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColorDark.copy(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = dec.format(it.balance) + " " + it.symbol,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold

                        )
                        Text(
                            text = "₦" + dec.format(
                                it.exchangeRate * it.balance
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColorDark.copy(0.5f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}
