package com.cakkie.ui.screens.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.screens.destinations.CreateShopDestination
import com.cakkie.ui.screens.destinations.ShopOnboardingDestination
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun ShopOnboarding(navigator: DestinationsNavigator) {
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    val pagerState = rememberPagerState(pageCount = { 3 })
    val uriHandle = LocalUriHandler.current
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(
                        id = R.string.cakkie_logo
                    ),
                    modifier = Modifier
                        .size(27.dp),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.shop),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    uriHandle.openUri("https://chat.whatsapp.com/Ll8zKZG32f9E5yObJGjmeu")
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.whatsapp_icon),
                    contentDescription = stringResource(
                        id = R.string.cakkie_logo
                    ),
                    modifier = Modifier
                        .size(24.dp),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.join_forum),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
//        Spacer(modifier = Modifier.fillMaxHeight(0.08f))
        HorizontalPager(state = pagerState) {
            Column(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(
                        id = when (it) {
                            0 -> R.drawable.rafiki
                            1 -> R.drawable.marketing
                            else -> R.drawable.amico
                        }
                    ),
                    contentDescription = stringResource(
                        id = when (it) {
                            0 -> R.string.wider_audience
                            1 -> R.string.more_sales
                            else -> R.string.strong_online_presence
                        }
                    ),
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp)
                        .height(height = height * 0.4f),
                )
//                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = stringResource(
                        id = when (it) {
                            0 -> R.string.wider_audience
                            1 -> R.string.more_sales
                            else -> R.string.strong_online_presence
                        }
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(
                        id = when (it) {
                            0 -> R.string.wider_audience_description
                            1 -> R.string.more_sales_desc
                            else -> R.string.strong_online_presence_desc
                        }
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                )
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = CakkieBrown,
            spacing = 8.dp,
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            pageCount = pagerState.pageCount,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        CakkieButton(
            text = stringResource(id = R.string.become_a_seller),
            modifier = Modifier.fillMaxWidth()
        ) {
            navigator.navigate(CreateShopDestination) {
                popUpTo(ShopOnboardingDestination) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}