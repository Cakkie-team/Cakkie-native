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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Listing
import com.cakkie.ui.components.ExpandImage
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class
)
@Destination
@Composable
fun ItemDetails(navigator: DestinationsNavigator) {
    var expanded by remember { mutableStateOf(false) }
    val imagePageState = rememberPagerState(pageCount = { 3 })
    val pageState = rememberPagerState(pageCount = { 2 })

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
                GlideImage(model = "https://source.unsplash.com/600x400/?cakes",
                    contentDescription = "cake",
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.FillBounds)
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
                text = "Strawberry Sponge cake",
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
                GlideImage(model = "https://source.unsplash.com/60x60/?profile",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(ProfileDestination)
                        })
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Cake Paradise",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "8 hours ago",
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
                    0 -> Description(navigator)
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