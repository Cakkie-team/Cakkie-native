package com.cakkie.ui.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AuthNotification(navigator: DestinationsNavigator) {

    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .fillMaxSize()
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
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = stringResource(id = R.string.was_this_you),
            style = MaterialTheme.typography.headlineMedium,
            color = CakkieBrown,

            )
        Spacer(modifier = Modifier.height(5.dp))


        Text(
            text = stringResource(id = R.string.unknown_device),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.padding(horizontal = 35.dp)) {

            Column() {
                Text(
                    text = stringResource(id = R.string.location),
                    style = MaterialTheme.typography.titleMedium,
                    color = CakkieBrown

                )
                Text(
                    text = stringResource(id = R.string.ikosi_ketu),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.lagos_nigeria),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(30.dp))

            Column() {
                Text(
                    text = stringResource(id = R.string.duration),
                    style = MaterialTheme.typography.titleMedium,
                    color = CakkieBrown

                )
                Text(
                    text = stringResource(id = R.string.greenish_meridian),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.date),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.device),
            style = MaterialTheme.typography.titleMedium,
            color = CakkieBrown

        )
        Text(
            text = stringResource(id = R.string.model),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(52.dp))

        CakkieButton(
            Modifier.height(50.dp),
            text = stringResource(id = R.string.yes_it_was_me)
        ) {}
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.no_it_was_not_me),
            style = MaterialTheme.typography.labelMedium
        )
    }
}