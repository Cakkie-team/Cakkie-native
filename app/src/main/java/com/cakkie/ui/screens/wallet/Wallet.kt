package com.cakkie.ui.screens.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Wallet(navigator: DestinationsNavigator) {

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
                        .padding(end=10.dp)
                        .align(Alignment.TopEnd), onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.recent),
                            contentDescription = "recents",
                            modifier = Modifier.size(24.dp),
                            tint = CakkieBackground
                        )
                    }
                    IconButton(modifier = Modifier
                        .padding(start=10.dp)
                        .align(Alignment.TopStart), onClick = { /*TODO*/ }) {
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
                            text = "200,000 NGN",
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
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(id = R.string.transaction_history),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.view_all),
                style = MaterialTheme.typography.bodySmall,
                color = CakkieBrown,
                modifier = Modifier.clickable {
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakicon),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = CakkieBackground,
                        shape = RoundedCornerShape(50)
                    )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.icing_purchase),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "100 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.withdrawal),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN10,000.00",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deposit),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN1,000.00",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakicon),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.icing_purchase),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "100 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.failed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Error
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deposit),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN12,000.00 ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.failed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Error
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cake_order),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "500 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakkie_icon),
                contentDescription = "",
                modifier = Modifier.background(CakkieBackground, RoundedCornerShape(50))
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.proposal_fee),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "50 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }

    }
}
