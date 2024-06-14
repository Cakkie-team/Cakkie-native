package com.cakkie.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Subscription(navigator: DestinationsNavigator) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Arrow Back",

            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    navigator.popBackStack()
                }
                .size(24.dp)
        )

        Text(
            text = stringResource(id = R.string.edit_shop),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 16.sp
        )

    }
}