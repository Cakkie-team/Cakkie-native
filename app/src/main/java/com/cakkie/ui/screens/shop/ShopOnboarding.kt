package com.cakkie.ui.screens.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun ShopOnboarding(navigator: DestinationsNavigator) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
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

        HorizontalPager(state = pagerState) {
            Image(
                painter = painterResource(id = R.drawable.rafiki),
                contentDescription = stringResource(id = R.string.wider_audience)
            )
        }



        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = CakkieBrown,
            spacing = 8.dp,
            indicatorWidth = 5.dp,
            indicatorHeight = 5.dp,
            pageCount = pagerState.pageCount,
            modifier = Modifier.offset(x = (-24).dp)
        )
    }
}