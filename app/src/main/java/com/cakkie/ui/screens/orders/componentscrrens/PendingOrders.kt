package com.cakkie.ui.screens.orders.componentscrrens


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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@Destination
@Composable
//fun PendingOrders(navigator: DestinationsNavigator) {
fun PendingOrders(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 33.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.today),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.today),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(80.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.progress), contentDescription = "")
            Column(modifier = Modifier.padding(start = 30.dp)) {
                Row {
                    Text(
                        text = stringResource(id = R.string.you_made_an_order_to),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = " Jane Doe ",
                        style = MaterialTheme.typography.labelSmall,
                        color = CakkieBrown,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = "20th May by 10:50 AM",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.view),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = CakkieBrown,
                modifier = Modifier.clickable { }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "19th June",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(80.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.progress), contentDescription = "")
            Column(modifier = Modifier.padding(start = 30.dp)) {
                Row {
                    Text(
                        text = stringResource(id = R.string.you_made_an_order_to),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = " Jane Doe ",
                        style = MaterialTheme.typography.labelSmall,
                        color = CakkieBrown,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = "20th May by 10:50 AM",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.view),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = CakkieBrown,
                modifier = Modifier.clickable { }
            )
        }
    }
}