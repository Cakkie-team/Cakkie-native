package com.cakkie.navigations

import com.cakkie.R
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.JobsDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavScreens(
    val direction: DirectionDestinationSpec,
    val title: String? = null,
    val icon: Int? = null,

    ) {
    ExploreScreen(
        direction = ExploreScreenDestination,
        title = "Explore",
        icon = R.drawable.explore,
    ),

    JobsScreen(
        direction = JobsDestination,
        title = "Jobs",
        icon = R.drawable.jobs,
    ),

    ShopScreen(
        direction = ShopDestination,
        title = "Shop",
        icon = R.drawable.shop,
    ),

    ChatScreen(
        direction = ChatDestination,
        title = "Chat",
        icon = R.drawable.chat,
    ),

    OrderScreen(
        direction = OrdersDestination,
        title = "Orders",
        icon = R.drawable.orders,
    )
}
