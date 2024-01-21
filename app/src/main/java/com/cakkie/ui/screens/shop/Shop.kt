package com.cakkie.ui.screens.shop

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
        if (true) {
            //navigate to create shop screen
            navigator.navigate(ShopOnboardingDestination) {
                popUpTo(ShopDestination) {
                    inclusive = true
                }
            }
        }
    }
    Text("Shop")
}