package com.cakkie.ui.screens.orders.componentscrrens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cakkie.R
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.screens.orders.components.CompleteItem
import com.cakkie.ui.screens.orders.components.InProgressItem
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun InProgress (navigator: DestinationsNavigator){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
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
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 70.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        CakkieFilter(navigator = navigator)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(5){
                InProgressItem()
            }
        }
    }
}