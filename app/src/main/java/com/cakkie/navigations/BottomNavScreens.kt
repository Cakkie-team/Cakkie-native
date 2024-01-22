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
    val title: Int? = null,
    val icon: Int? = null,

    ) {
    ExploreScreen(
        direction = ExploreScreenDestination,
        title = R.string.explore,
        icon = R.drawable.explore,
    ),

    JobsScreen(
        direction = JobsDestination,
        title = R.string.jobs,
        icon = R.drawable.jobs,
    ),

    ShopScreen(
        direction = ShopDestination,
        title = R.string.shop,
        icon = R.drawable.shop,
    ),

    ChatScreen(
        direction = ChatDestination,
        title = R.string.chat,
        icon = R.drawable.chat,
    ),

    OrderScreen(
        direction = OrdersDestination,
        title = R.string.orders,
        icon = R.drawable.orders,
    )
}
