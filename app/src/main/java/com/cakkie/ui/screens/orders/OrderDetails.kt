package com.cakkie.ui.screens.orders


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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.networkModels.Order
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.formatDate
import com.cakkie.utill.formatDateTime
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun OrderDetails(item: Order, navigator: DestinationsNavigator) {
    val meta = listOf(
        Pair("Size", "${item.meta.size} inches"),
        Pair("Flavour", item.meta.flavour),
        Pair("Price", "N ${item.unitPrice * item.quantity}"),
        Pair("Quantity", item.quantity.toString()),
    )
    val openDialog = remember {
        mutableStateOf(false)
    }
    var showCode by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    },
            )
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            IconButton(
                onClick = {
                    navigator.navigate(ChatDestination("support"))
                }, modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.support),
                    contentDescription = stringResource(
                        id = R.string.support
                    ),
                    modifier = Modifier
                        .size(27.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.ordered_from),
                style = MaterialTheme.typography.bodyLarge,

                )
            Text(
                text = " ${item.shop.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown
            )
        }
        Text(
            text = "on ${item.createdAt.formatDateTime()}",
            style = MaterialTheme.typography.bodyLarge,
            color = TextColorDark.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            color = CakkieBrown,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        meta.filterIndexed { index, _ -> index % 2 == 0 }
            .zip(meta.filterIndexed { index, _ -> index % 2 != 0 }).forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        Text(
                            text = it.first.first,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = it.first.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = it.second.first,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = it.second.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }

        Spacer(modifier = Modifier.height(5.dp))
        Column {
            Text(
                text = stringResource(id = R.string.delivery_address),
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(
                        id = R.drawable.location
                    ),
                    contentDescription = "location",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = item.deliveryAddress,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Image(
                        painter = painterResource(
                            id = R.drawable.done
                        ),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = item.status.lowercase(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.waiting_time),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = item.waitTime.formatDate(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CakkieButton(
                text = stringResource(
                    id = when (item.status) {
                        "PENDING" -> R.string.cancel
                        else -> R.string.generate_code
                    }
                ),
                enabled = item.status == "PENDING" || item.status == "ARRIVED",
            ) {
                if (item.status == "PENDING") {
                    openDialog.value = true
                } else {
                    showCode = true
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = item.id.takeLast(6),
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .blur(if (showCode) 0.dp else 10.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Reveal code to delivery agent only when you receive your order",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
        )
    }

    if (openDialog.value)
        Popup(
            onDismissRequest = { openDialog.value = false },
            alignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = CakkieBrown,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
                    .background(CakkieBackground, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cakkie_icon),
                            contentDescription = ""
                        )
                        Text(
                            text = stringResource(id = R.string.warning),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.are_you_sure_you_want_to_cancel_this_order),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = CakkieBrown,
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.no),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = CakkieBrown,
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
}


