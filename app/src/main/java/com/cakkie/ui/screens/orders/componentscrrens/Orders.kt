package com.cakkie.ui.screens.orders.componentscrrens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Orders(navigator: DestinationsNavigator) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.cakkie_logo
                ),
                modifier = Modifier
                    .size(27.dp),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.width(6.dp))
            androidx.compose.material.Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = CakkieBrown,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.construct),
                contentDescription = "under construction",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(id = R.string.under_construction),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.something_is_baking_here),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Spacer(modifier = Modifier.height(40.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp),
//        ) {
//            Image(
//                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .clickable {
//                        navigator.popBackStack()
//                    },
//            )
//            Text(
//                text = stringResource(id = R.string.orders),
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//     CakkieFilter(navigator = navigator)
//        Spacer(modifier = Modifier.height(16.dp))
//
//            LazyColumn {
// items(5){
//     OrdersItem()
//     }
//            }
//        }
}