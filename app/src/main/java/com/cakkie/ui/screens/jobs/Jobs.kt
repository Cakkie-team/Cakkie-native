package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Jobs(
    fileRecipient: ResultRecipient<ChooseMediaDestination, String>,
    navigator: DestinationsNavigator
) {
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    val pageState = rememberPagerState(pageCount = { 4 })
    val media = remember {
        mutableStateListOf<MediaModel>()
    }
    Column(Modifier) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
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
                text = stringResource(id = R.string.jobs),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = CakkieBrown,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(Modifier.height(height.minus(88.dp))) {
            //page tabs
            PageTabs(
                pagerState = pageState,
                pageCount = pageState.pageCount,
                tabs = listOf(
                    stringResource(id = R.string.all_jobs),
                    stringResource(id = R.string.post_a_job),
                    stringResource(id = R.string.my_jobs),
                )
            )

            HorizontalPager(state = pageState) {
                when (it) {
                    2 -> AllJobs()
                    1 -> CreateJob(media, fileRecipient, navigator = navigator)
                    0 -> AllJobs()
                }
            }
        }
    }
}