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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.cakkie.ui.screens.destinations.Destination
import com.cakkie.ui.screens.destinations.RecieptDestination
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun Orders1 (navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
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
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.ordered_from_cakkie),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = " Jane Doe",
                style = MaterialTheme.typography.labelMedium,
                color = CakkieBrown
            )
        }
        Text(
            text = "Posted 19th June",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            color = CakkieBrown,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))

        Row(
            modifier = Modifier
                .padding(start = 16.dp)
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
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = stringResource(id = R.string.medium_sized_pan),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.width(70.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.proposed_price),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "NGN 20,000",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
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
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(modifier = Modifier.width(1300.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.location),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Lagos State",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
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
                    painter = painterResource(id = R.drawable.gridicons_heart),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(150.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.icing),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "300 ",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        CakkieButton(text = stringResource(id = R.string.see_reciept)
        ) {
            navigator.navigate(RecieptDestination())
        }
    }
}

    
