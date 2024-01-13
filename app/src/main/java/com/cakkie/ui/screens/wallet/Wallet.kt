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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

@Destination
@Composable
fun Wallet() {
    var sizeImage by remember {
        mutableStateOf(IntSize.Zero)
    }
    val gradient = Brush.linearGradient(
        0.0f to CakkieBackground.copy(0.2f),
        500.0f to Color.Transparent,
        start = Offset.Zero,
        end = Offset.Infinite,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(TextColorDark)
                .padding(start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back_fill),
                        contentDescription = "",
                        modifier = Modifier.clickable { }
                    )
                    Text(
                        text = stringResource(id = R.string.wallet),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color = Color.Transparent)
                        .border(
                            width = 0.8.dp,
                            color = CakkieBackground,
                            shape = RoundedCornerShape(15)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(gradient)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = R.string.total_balance),
                            style = MaterialTheme.typography.labelSmall,
                            color = CakkieBackground
                        )
                        Text(
                            text = "NGN33,000.00",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 25.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(25.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Column(verticalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "Naira Balance",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CakkieBackground
                                )
                                Text(
                                    text = "NGN20,000.00",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Icing Balance",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CakkieBackground
                                )
                                Row {
                                    Text(
                                        text = "300",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White
                                    )
                                    Text(
                                        text = " icing",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CakkieBackground,
                                        modifier = Modifier.paddingFromBaseline(bottom = 12.dp)

                                    )
                                }
                                Text(
                                    text = "NGN13,000.00",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CakkieBackground
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CakkieButton(
                        text = stringResource(id = R.string.withdraw),
                        contentColor = CakkieBrown,
                        color = CakkieBackground
                    ) {
                    }
                    CakkieButton(
                        text = stringResource(id = R.string.deposit),
                        contentColor = CakkieBrown,
                        color = CakkieBackground
                    ) {
                    }
                    CakkieButton(
                        text = stringResource(id = R.string.buy_icing),
                        contentColor = CakkieBrown,
                        color = CakkieBackground
                    ) {
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
