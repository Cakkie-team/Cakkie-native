package com.cakkie.ui.screens.orders


import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun OrderDetails(item: Int, navigator: DestinationsNavigator) {
    val meta = listOf(
        Pair("Size", "6 inches"),
        Pair("Flavour", "Chocolate"),
        Pair("Price", "N 3000"),
        Pair("Quantity", "1"),
        Pair("Shape", "Round"),
    )
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
                text = stringResource(id = R.string.ordered_from_cakkie),
                style = MaterialTheme.typography.bodyLarge,

                )
            Text(
                text = " Jane Doe",
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown
            )
        }
        Text(
            text = "on 19th June",
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
            text = "HI, I would like to klnfn jwfjwkh jiefj;wi hfwiu hrif hukseh fuio " +
                    "woyrory r yrororywer;iorryo row;tyr8tw8o y8wyoiy/iy o ryhfo /yoryi r" +
                    " wioyoi uw/wyf8uyhriof y rwry8roryuirtg.8o fwry.riuyrukyr8ir.rufyr.grog8y/o",
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
                    text = "No 1, Cakkie Street, Cakkie Town, Cakkie State",
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
                        text = stringResource(id = R.string.in_progress),
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
                    text = "3 Days 14 Hours",
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
                text = stringResource(id = R.string.generate_code)
            ) {
                navigator.navigate(ChatDestination(""))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "234657",
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .blur(10.dp)
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
}


