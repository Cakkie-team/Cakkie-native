package com.cakkie.navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.cakkie.R
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.appCurrentDestinationAsState
import com.cakkie.ui.screens.destinations.Destination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.startAppDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Inactive
import com.ramcosta.composedestinations.navigation.navigate


@Composable
fun BottomNav(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier,
) {
//     list screens to show in bottom nav
    val screens = listOf(
        BottomNavScreens.ExploreScreen,
        BottomNavScreens.JobsScreen,
        BottomNavScreens.ShopScreen,
        BottomNavScreens.ChatScreen,
        BottomNavScreens.OrderScreen
    )

    AnimatedVisibility(
        visible = state,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        //            val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: Destination =
            navController.appCurrentDestinationAsState().value
                ?: NavGraphs.root.startAppDestination

        Card(
            modifier = Modifier
                .shadow(80.dp)
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            elevation = 50.dp
        ) {
            Box(
                modifier = modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = modifier
                        .align(Alignment.BottomCenter)
                        .background(CakkieBackground)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    screens.forEach { screen ->
                        if (screen == BottomNavScreens.ShopScreen) {
                            Spacer(modifier = Modifier.width(90.dp))
                        } else {
                            Column(
                                Modifier
                                    .padding(
                                        start = if (screen.direction == ExploreScreenDestination) 10.dp else 0.dp,
                                        end = if (screen.direction == OrdersDestination) 10.dp else 0.dp
                                    )
                                    .background(Color.Transparent)
                                    .clickable {
                                        navController.navigate(
                                            screen.direction,
                                            fun NavOptionsBuilder.() {
                                                launchSingleTop = true
                                            })
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.line),
                                    contentDescription = null,
                                    tint = if (currentDestination == screen.direction) CakkieBrown else Color.Transparent,
                                    modifier = Modifier
                                        .height(3.dp)
                                        .width(38.dp),
                                )
                                Spacer(modifier = Modifier.height(7.dp))
                                Icon(
                                    painter = painterResource(screen.icon!!),
                                    contentDescription = screen.title,
                                    tint = if (currentDestination == screen.direction) CakkieBrown else Color.Gray,
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(24.dp)
                                        .padding(bottom = 5.dp),
                                )
                                Text(
                                    text = screen.title!!,
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.body1,
                                    color = if (currentDestination == screen.direction) CakkieBrown else Inactive,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                            }
                        }
                    }
                }

                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(CakkieBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        Modifier
                            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                            .clickable {
                                navController.navigate(
                                    screens.find { it.title == "Shop" }!!.direction,
                                    fun NavOptionsBuilder.() {
                                        launchSingleTop = true
                                    })
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .background(CakkieBrown)
                                .size(60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(screens.find { it.title == "Shop" }!!.icon!!),
                                contentDescription = screens.find { it.title == "Shop" }!!.title,
                                tint = CakkieBackground,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        Text(
                            text = screens.find { it.title == "Shop" }!!.title!!,
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.body1,
                            color = if (currentDestination == screens.find { it.title == "Shop" }!!
                                    .direction
                            ) CakkieBrown else Inactive,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                    }
                }
            }
        }
    }
}
