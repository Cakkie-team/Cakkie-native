package com.cakkie.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.cakkie.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ItemDetails(navigator: DestinationsNavigator) {
    Column {
        IconButton(onClick = { navigator.popBackStack() }) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back",
//                modifier = Modifier.height(24.dp)
            )
        }
    }
}