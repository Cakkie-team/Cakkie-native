package com.cakkie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakkie.navigations.BottomNav
import com.cakkie.ui.components.CakkieFilter2
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.appCurrentDestinationAsState
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.JobsDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.SplashScreenDestination
import com.cakkie.ui.screens.orders.CancelOrder
import com.cakkie.ui.screens.orders.CompletedOrder
import com.cakkie.ui.screens.orders.InProgressOrder
import com.cakkie.ui.screens.orders.OrderState
import com.cakkie.ui.screens.orders.Reciept
import com.cakkie.ui.screens.orders.components.OrdersItem
import com.cakkie.ui.screens.orders.componentscrrens.Completed
import com.cakkie.ui.screens.orders.componentscrrens.CompletedOrders
import com.cakkie.ui.screens.orders.componentscrrens.OngoingOrder
import com.cakkie.ui.screens.orders.componentscrrens.PendingOrders
import com.cakkie.ui.screens.wallet.IcingPurchase
import com.cakkie.ui.screens.wallet.Reciept1
import com.cakkie.ui.screens.wallet.Reciept2
import com.cakkie.ui.screens.wallet.Wallet
import com.cakkie.ui.screens.wallet.WalletHistory
import com.cakkie.ui.screens.wallet.WalletHistory1
import com.cakkie.ui.screens.wallet.WalletWithdrawal
import com.cakkie.ui.theme.BackgroundImageId
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine


//@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
//var sheetState: BottomSheetNavigatorSheetState = BottomSheetNavigatorSheetState(
//    sheetState = ModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        density = Density(1f),
////        animationSpec = SwipeableDefaults.AnimationSpec
//    )
//)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberAnimatedNavController(bottomSheetNavigator)
//            sheetState = bottomSheetNavigator.navigatorSheetState
            //current destination
            val currentDestination = navController.appCurrentDestinationAsState().value
            CakkieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ModalBottomSheetLayout(
                        bottomSheetNavigator = bottomSheetNavigator,
                        sheetBackgroundColor = CakkieBackground,
                        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = BackgroundImageId(isSystemInDarkTheme())),
                                contentDescription = "background",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                                                    Box(
                                Modifier.padding(
                                    top = if (currentDestination == SplashScreenDestination) 0.dp
                                    else 30.dp
                                )
                            ) {
                                                        DestinationsNavHost(
                                    navGraph = NavGraphs.root,
                                    navController = navController,
                                    modifier = Modifier
                                        .padding(
                                            bottom =
                                            when (currentDestination) {
                                                ExploreScreenDestination -> 50.dp
                                                JobsDestination -> 50.dp
                                                ShopDestination -> 50.dp
                                                ChatDestination -> 50.dp
                                                OrdersDestination -> 50.dp
                                                else -> 0.dp
                                            }
                                        )
                                        .fillMaxSize(),
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
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                )
                        }
                    }
                }
            }
        }
    }
}

//@ExperimentalMaterialNavigationApi
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun rememberBottomSheetNavigator(
//    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
//    skipHalfExpanded: Boolean = false,
//): BottomSheetNavigator {
//    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        animationSpec = animationSpec,
//        skipHalfExpanded = skipHalfExpanded,
//    )
//    return remember(sheetState) {
//        BottomSheetNavigator(sheetState = sheetState)
//    }
//}
