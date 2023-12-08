package com.cakkie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakkie.navigations.BottomNav
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.appCurrentDestinationAsState
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.JobsDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.SplashScreenDestination
import com.cakkie.ui.theme.BackgroundImageId
import com.cakkie.ui.theme.CakkieTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.BottomSheetNavigatorSheetState
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine


@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
var sheetState: BottomSheetNavigatorSheetState = BottomSheetNavigatorSheetState(
    sheetState = ModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = SwipeableDefaults.AnimationSpec
    )
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator(skipHalfExpanded = true)
            val navController = rememberAnimatedNavController(bottomSheetNavigator)
            sheetState = bottomSheetNavigator.navigatorSheetState
            //current destination
            val currentDestination = navController.appCurrentDestinationAsState().value
            CakkieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ModalBottomSheetLayout(
//                        bottomSheetNavigator = bottomSheetNavigator
//                    ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = BackgroundImageId(isSystemInDarkTheme())),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                        Column(
                            Modifier.padding(
                                top = if (currentDestination == SplashScreenDestination) 0.dp
                                else 30.dp
                            )
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = navController,
                                modifier = Modifier.weight(1f),
                                engine = rememberAnimatedNavHostEngine()
                            )
                            BottomNav(
                                navController = navController,
                                state = when (currentDestination) {
                                    ExploreScreenDestination -> true
                                    JobsDestination -> true
                                    ShopDestination -> true
                                    ChatDestination -> true
                                    OrdersDestination -> true
                                    else -> false
                                },
                            )
                        }
                    }
//                    }
                }
            }
        }
    }
}


@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    skipHalfExpanded: Boolean = false,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = animationSpec,
        skipHalfExpanded = skipHalfExpanded,
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}
