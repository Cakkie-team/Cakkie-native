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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun CompletedOrder (navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
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
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.ordered_from_cakkie),
                style = MaterialTheme.typography.labelMedium,

                )
            Text(
                text = " Jane Doe",
                style = MaterialTheme.typography.labelMedium,
                color = CakkieBrown
            )
        }
        Text(
            text = "Posted 19th June",
            style = MaterialTheme.typography.bodySmall,
            color = TextColorDark.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            color = CakkieBrown,
        )
        Spacer(modifier = Modifier.height(20.dp))
Text(text = "HI, I would like to klnfn  jwfjwkh jiefj;wi hfwiu hrif hukseh fuio " +
        "woyrory r yrororywer;iorryo   row;tyr8tw8o \n y8wyoiy/iy o ryhfo /yoryi r" +
        " wioyoi uw/wyf8uyhriof y rwry8roryuirtg.8o  fwry.riuyrukyr8ir.rufyr.grog8y/o",
    style = MaterialTheme.typography.bodySmall
)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(id = R.string.size),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "6 inches:",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(id = R.string.medium_sized_pan),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.proposed_price),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "NGN 20,000",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 35.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.shape),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.rounded_shape),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.location),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Lagos State",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 60.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.badge
                    ),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.icing),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "300 ",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        CakkieButton(text = stringResource(id = R.string.see_reciept)
        ) {
//            navigator.navigate(RecieptDestination())
        }
    }
}

    
