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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.R
import com.cakkie.networkModels.Balance
import com.cakkie.networkModels.Transaction
import com.cakkie.networkModels.TransactionResponse
import com.cakkie.ui.screens.destinations.EarnDestination
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.wallet.history.HistoryItem
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
fun AssetDetails(item: Balance = Balance(), navigator: DestinationsNavigator) {
    val dec = DecimalFormat("#,##0.00")
    val viewModel: WalletViewModel = koinViewModel()
    val history = viewModel.transaction.observeAsState(TransactionResponse()).value
    val trans = remember {
        mutableStateListOf<Transaction>()
    }
    LaunchedEffect(key1 = item) {
        viewModel.getTransactions(item.id)
    }

    LaunchedEffect(key1 = history) {
        trans.addAll(history?.data?.filterNot { res ->
            trans.any { it.id == res.id }
        } ?: emptyList())
    }

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
                    .width(24.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = item.name,
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
                        var isLoading by remember {
                            mutableStateOf(false)
                        }
                        AsyncImage(
                            model = item.icon,
                            contentDescription = "currency icon",
                            onState = {
                                //update isLoaded
                                isLoading = it is AsyncImagePainter.State.Loading
                            },
                            modifier = Modifier
                                .background(CakkieBackground, CircleShape)
                                .size(40.dp)
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
                            contentScale = ContentScale.Fit,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dec.format(item.balance) + " " + item.symbol,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        if (item.symbol == "SPK") {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        navigator.navigate(EarnDestination)
                                    }
                                    .background(CakkieBackground, RoundedCornerShape(8.dp))
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.earn),
                                    contentDescription = "Deposit",
                                    modifier = Modifier.size(24.dp),
                                    tint = CakkieBrown
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Earn",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBrown,
                                    fontSize = 16.sp
                                )
                            }
                        } else {

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
                items = trans,
                key = { it.id }
            ) {
                val index = trans.lastIndexOf(it)
                if (index > trans.lastIndex - 2 && history.data.isNotEmpty()) {
                    viewModel.getTransactions(item.id, history.meta.nextPage, history.meta.pageSize)
                }
                HistoryItem(it)
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (trans.isEmpty()) {
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