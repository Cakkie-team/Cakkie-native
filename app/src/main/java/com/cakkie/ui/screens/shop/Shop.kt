package com.cakkie.ui.screens.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.ShopOnboardingDestination
import com.cakkie.ui.screens.shop.contracts.Contracts
import com.cakkie.ui.screens.shop.contracts.Listings
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
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

    val pageState = rememberPagerState(pageCount = { 4 })
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(id = R.string.shop),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = CakkieBrown,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

        //page tabs
        PageTabs(
            pagerState = pageState,
            pageCount = pageState.pageCount,
            tabs = listOf(
                stringResource(id = R.string.contracts),
                stringResource(id = R.string.proposals),
                stringResource(id = R.string.requests),
                stringResource(id = R.string.listings)
            )
        )
        HorizontalPager(state = pageState) {
            when (it) {
                0 -> Contracts()
                1 -> Contracts()
                2 -> Listings(navigator)
                3 -> Listings(navigator)
            }
        }
    }
}