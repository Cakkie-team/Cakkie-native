package com.cakkie.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Subscription(navigator: DestinationsNavigator) {
    var subType by remember {
        mutableIntStateOf(0)
    }
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.sub),
                contentDescription = "Subscription",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.sub1),
                contentDescription = "Subscription",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.5f),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { subType = 0 }
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            Row(
                Modifier
                    .padding(start = 10.dp, end = 18.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = subType == 0,
                        onCheckedChange = {
                            subType = 0
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = CakkieBrown,
                            uncheckedColor = CakkieBrown002
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.annually),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "NGN1900.00/month",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { subType = 1 }
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            Row(
                Modifier
                    .padding(start = 10.dp, end = 18.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = subType == 1,
                        onCheckedChange = {
                            subType = 1
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = CakkieBrown,
                            uncheckedColor = CakkieBrown002
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.monthly),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "NGN1900.00/month",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        listOf(
            "Premium membership badge",
            "Unlimited access to all features",
            "High visibility priority",
            "24/7 customer support",
            "Extended storage",
        ).forEach {
            Row(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = CakkieBrown,
                        uncheckedColor = CakkieBrown002
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        CakkieButton(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = stringResource(id = R.string.subscribe),
            processing = false,
        ) {
//            navigator.navigate(SubscriptionSuccess)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}