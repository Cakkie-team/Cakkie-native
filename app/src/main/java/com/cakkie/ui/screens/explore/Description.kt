package com.cakkie.ui.screens.explore

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.User
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.SetDeliveryAddressDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Description(user: User?, item: Listing, navigator: DestinationsNavigator) {
    val sizes = item.sizes.map { "$it in" }
    var selectedSize by remember { mutableStateOf(sizes.first()) }
    Column {
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.size) + ":",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(Modifier.padding(horizontal = 16.dp)) {
            items(
                items = sizes
            ) {
                Card(
                    onClick = { selectedSize = it },
                    colors = CardDefaults.cardColors(
                        containerColor =
                        if (selectedSize == it) CakkieBrown
                        else CakkieBackground
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = CakkieBrown.copy(alpha = 0.4f)
                    ),
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        color = if (selectedSize == it) CakkieBackground
                        else CakkieBrown.copy(alpha = 0.4f)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.availability) + ":",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = item.availablity,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.delivery_address),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = user?.address ?: "No address",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { navigator.navigate(SetDeliveryAddressDestination) }
                    .weight(1f),
                color = CakkieBrown,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        listOf(
            R.string.price,
            R.string.delivery_fee,
            R.string.total
        ).forEach {
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = if (it == R.string.total) 16.sp else 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = when (it) {
                        R.string.price -> "NGN ${
                            NumberFormat.getInstance()
                                .format(item.price[sizes.indexOf(selectedSize)])
                        }"

                        R.string.delivery_fee -> "NGN 1,000"
                        else -> "NGN ${
                            NumberFormat.getInstance()
                                .format(item.price[sizes.indexOf(selectedSize)].plus(1000))
                        }"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = if (it == R.string.total) 16.sp else 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        CakkieButton(
            text = stringResource(id = R.string.order),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {

        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}