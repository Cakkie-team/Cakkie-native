package com.cakkie.ui.screens.explore

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Description(navigator: DestinationsNavigator) {
    val sizes = listOf("6 in", "8 in", "10 in")
    var selectedSize by remember { mutableStateOf(sizes.first()) }
    Column {
        Text(
            text = "Indulge in the heavenly delight of our signature cake. Fluffy layers, creamy goodness, and fresh fruits combine for a slice of paradise. Join us and savor the sweetness! \uD83C\uDF70✨ \n" +
                    "\n" +
                    "#CakeLove #DessertParadise",
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
            text = "Please note that under normal circumstances, this cake requires a minimum of 24 hours to prepare and will be delivered to you within 24 hours after preparation.",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
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
                        R.string.price -> "NGN 20,000"
                        R.string.delivery_fee -> "NGN 1,000"
                        else -> "NGN 21,000"
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
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}