package com.cakkie.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.cakkie.R
import com.cakkie.ui.screens.destinations.EmailScreenDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.SplashScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Destination
@RootNavGraph(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
    val viewModel: SplashViewModel = koinViewModel()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
    //navigate to the next screen after 2 seconds
    LaunchedEffect(key1 = Unit) {
        delay(3000)
        if (isLoggedIn) {
            navigator.navigate(ExploreScreenDestination) {
                popUpTo(SplashScreenDestination.route) {
                    inclusive = true
                }
            }
        } else {
            navigator.navigate(EmailScreenDestination) {
                popUpTo(SplashScreenDestination.route) {
                    inclusive = true
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}