package com.cakkie.ui.screens.wallet.earn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieOrange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Earn(navigator: DestinationsNavigator) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            IconButton(modifier = Modifier
                .align(Alignment.CenterStart), onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = "Earning Dashboard",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                color = CakkieBrown,
                fontSize = 18.sp
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(CakkieBrown)
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Current Balance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .background(CakkieBrown002, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "5,000.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "50",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "ICING",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Earning Rate",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(3.dp))

                Box(
                    modifier = Modifier
                        .background(CakkieBrown002, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "+50.20",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieOrange,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "icing/hr",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(CakkieBrown002.copy(0.8f), RoundedCornerShape(50))
                .clip(RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Next session starts",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "00:23:04",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieOrange,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }

}