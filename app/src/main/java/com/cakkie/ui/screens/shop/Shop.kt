package com.cakkie.ui.screens.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.ShopOnboardingDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun Shop(navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value

    //check if user is has a shop
    LaunchedEffect(key1 = user) {
        if (user?.hasShop == true) {
            //navigate to create shop screen
            navigator.navigate(ShopOnboardingDestination) {
                popUpTo(ShopDestination) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    Column(Modifier.fillMaxSize()) {
        Text(
            stringResource(id = R.string.shop),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}