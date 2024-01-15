package com.cakkie.ui.screens.settings

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.AuthNotificationDestination
import com.cakkie.ui.screens.destinations.ChangePasswordDestination
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SecurityItem(email: String, navigator: DestinationsNavigator) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CakkieLightBrown)
    )


    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigator.navigate(ChangePasswordDestination()) }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(

            ) {
                Image(
                    painterResource(id = R.drawable.lock),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                    Text(
                        text = stringResource(id = R.string.change_password),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

            }

            Image(
                painterResource(id = R.drawable.arrow_right),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)


            )
        }

    }

}