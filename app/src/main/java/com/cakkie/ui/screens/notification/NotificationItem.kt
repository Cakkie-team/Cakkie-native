package com.cakkie.ui.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.AuthNotificationDestination
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun NotificationItem(isBackground: Boolean, navigator: DestinationsNavigator) {
    Row {
        if (isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "delete",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(65.dp)
                .clickable {
                    navigator.navigate(
                        AuthNotificationDestination
                    )
                }

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = stringResource(id = R.string.time),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextColorDark.copy(alpha = 0.5f),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                style = MaterialTheme.typography.bodyLarge,
                color = TextColorDark.copy(alpha = 0.5f),
            )
        }
    }

}