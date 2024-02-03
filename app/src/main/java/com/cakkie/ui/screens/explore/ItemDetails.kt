package com.cakkie.ui.screens.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.formatDate
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@OptIn(
    ExperimentalFoundationApi::class
)
@Destination
@Composable
fun ItemDetails(id: String, item: Listing = Listing(), navigator: DestinationsNavigator) {
    var expanded by remember { mutableStateOf(false) }
    val imagePageState =
        rememberPagerState(pageCount = { if (item.media.isEmpty()) 1 else item.media.size })
    val pageState = rememberPagerState(pageCount = { 2 })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column {
        IconButton(onClick = { navigator.popBackStack() }) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back", contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(24.dp)
            )
        }
        Column(Modifier.verticalScroll(rememberScrollState())) {
            HorizontalPager(state = imagePageState) {
                GlideImage(
                    imageModel = item.media[it],
                    contentDescription = "cake",
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .fillMaxWidth()
                        .height(screenWidth),
                    contentScale = ContentScale.Crop,
                    shimmerParams = ShimmerParams(
                        baseColor = CakkieBrown.copy(0.4f),
                        highlightColor = CakkieBrown.copy(0.8f),
                        dropOff = 0.55f,
                        tilt = 20f
                    )
                )
            }

            Row(
                Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalPagerIndicator(
                    pagerState = imagePageState,
                    activeColor = CakkieBrown,
                    spacing = 8.dp,
                    indicatorWidth = 5.dp,
                    indicatorHeight = 5.dp,
                    pageCount = pageState.pageCount,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                GlideImage(
                    imageModel = item.shop.image,
                    contentDescription = "shop logo",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(ProfileDestination)
                        }, shimmerParams = ShimmerParams(
                        baseColor = CakkieBrown.copy(0.4f),
                        highlightColor = CakkieBrown.copy(0.8f),
                        dropOff = 0.55f,
                        tilt = 20f
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = item.shop.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = item.createdAt.formatDate(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            //page tabs
            PageTabs(
                pagerState = pageState, pageCount = pageState.pageCount,
                tabs = listOf(
                    stringResource(id = R.string.description),
                    stringResource(id = R.string.reviews)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            //page
            HorizontalPager(state = pageState) {
                when (it) {
                    0 -> Description(item, navigator)
                    1 -> Reviews()
                }
            }
        }
    }


    ExpandImage(
        item = Listing(),
        expanded = expanded,
        onDismiss = { expanded = false },
        navigator = navigator,
        pageState = imagePageState
    )
}