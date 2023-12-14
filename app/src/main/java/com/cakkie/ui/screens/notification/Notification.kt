package com.cakkie.ui.screens.notification

import android.text.Layout.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Notification(navigator: DestinationsNavigator) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.padding(16.dp)


            ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier
                    .clickable {
                        navigator.popBackStack()
                    }
                    .size(24.dp)
            )

            Text(
                text = stringResource(id = R.string.notification),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 60.dp)

                )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .height(65.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.Special_Offer),
                    style = MaterialTheme.typography.titleSmall

                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.Limited_Time_Offer_Enjoy_),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier,

                    )
            }
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}