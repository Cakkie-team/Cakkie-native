package com.cakkie.navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.cakkie.R
import com.cakkie.ui.screens.NavGraphs
import com.cakkie.ui.screens.appCurrentDestinationAsState
import com.cakkie.ui.screens.destinations.Destination
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
    ) {
        BottomNavigation(
            backgroundColor = CakkieBackground,
            elevation = 30.dp,
        ) {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                ?: NavGraphs.root.startAppDestination

            screens.forEach { screen ->

                BottomNavigationItem(
                    label = {
                        Text(
                            text = screen.title!!,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W700,
                            style = MaterialTheme.typography.body1,
                        )
                    },

                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        }
                    },
                    selected = currentDestination == screen.direction,
                    onClick = {
                        navController.navigate(screen.direction, fun NavOptionsBuilder.() {
                            launchSingleTop = true
                        })
                    },

                    alwaysShowLabel = true,
                    selectedContentColor = CakkieBrown,
                    unselectedContentColor = Inactive,
                )
            }
        }
    }
}
