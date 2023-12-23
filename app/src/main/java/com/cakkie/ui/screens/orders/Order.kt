package com.cakkie.ui.screens.orders

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.core.context.stopKoin
@Destination
@Composable
fun Order (navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
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
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.sponge_cake),
                contentDescription = "cake"
            )
            Text(
                text = stringResource(id = R.string.strawberry_sponge_cake),
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.log), contentDescription = "")
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.cake_paradise),
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Normal
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.hours_ago),
                        style = MaterialTheme.typography.displaySmall,
                        fontStyle = FontStyle.Normal,
                        color = TextColorDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Normal,
                color = CakkieBrown,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(50.dp),
                thickness = 1.dp,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.indulge_in_the_heavenly_delight_of_our_signature_cake_fluffy_layers_creamy_goodness_and_fresh_fruits_combine_for_a_slice_of_paradise_join_us_and_savor_the_sweetness),
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.cake_love_dessert_paradise),
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.size),
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .size(width = 80.dp, height = 28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBrown,
                        contentColor = CakkieLightBrown
                    ),
                    shape = RoundedCornerShape(10),
                ) {
                    Text(
                        text = "6 in",
                        color = CakkieBackground,
                    )
                }
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(width = 80.dp, height = 34.dp)
                        .padding(start = 16.dp)
                        .border(width = 2.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = CakkieLightBrown
                    ),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(
                        text = "8 in",
                        color = CakkieLightBrown
                    )
                }
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(width = 80.dp, height = 34.dp)
                        .padding(start = 16.dp)
                        .border(width = 2.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = CakkieLightBrown
                    ),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(
                        text = "10 in",
                        color = CakkieLightBrown
                    )
                }
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(width = 80.dp, height = 34.dp)
                        .padding(start = 16.dp)
                        .border(width = 2.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = CakkieLightBrown
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(
                        text = "12 in",
                        color = CakkieLightBrown
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.availability),
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.please_note_that_under_normal_circumstances_this_cake_requires_a_minimum_of_24_hours_to_prepare_and_will_be_delivered_to_you_within_24_hours_after_preparation),
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.price),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Normal,
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.40f))
                Text(
                    text = "NGN 15,OOO",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(start = 90.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.delivery_fee),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Normal,
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.30f))
                Text(
                    text = "NGN 7OO",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(start = 90.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.total),
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Normal,

                    )
                Spacer(modifier = Modifier.fillMaxWidth(0.65f))
                Text(
                    text = "NGN 15,7OO",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Normal,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            CakkieButton(
                text = stringResource(id = R.string.order)
            ) {
navigator.navigate(CompletedOrderDestination)
            }
        }
    }
}