package com.cakkie.ui.screens.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.data.db.models.ShopModel
import com.cakkie.ui.components.PageTabs
import com.cakkie.ui.screens.destinations.EditShopDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.destinations.ShopOnboardingDestination
import com.cakkie.ui.screens.shop.contracts.Contracts
import com.cakkie.ui.screens.shop.contracts.Proposals
import com.cakkie.ui.screens.shop.listings.Listings
import com.cakkie.ui.screens.shop.listings.Requests
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun Shop(navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    val shop = viewModel.shop.observeAsState(ShopModel()).value
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    //check if user is has a shop
    LaunchedEffect(key1 = user) {
        if (user?.hasShop == false) {
            //navigate to create shop screen
            navigator.navigate(ShopOnboardingDestination) {
                popUpTo(ShopDestination) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            viewModel.getMyShop()
            viewModel.getMyListings()
            viewModel.getRequests()
            viewModel.getContracts()
        }
    }

    val pageState = rememberPagerState(pageCount = { 4 })
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            GlideImage(
                model = shop.image,
                contentDescription = shop?.name,
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
            Row(
                Modifier
                    .align(Alignment.Bottom)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = shop.followers.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = stringResource(id = R.string.followers),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Divider(
                    color = CakkieBrown,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .size(width = 70.dp, height = 34.dp),
                    border = BorderStroke(1.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBackground,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share, contentDescription = "",
                        modifier = Modifier,
                        tint = CakkieBrown
                    )
                }
                OutlinedButton(
                    onClick = {
                        navigator.navigate(EditShopDestination)
                    },
                    modifier = Modifier
                        .size(width = 70.dp, height = 34.dp),
                    border = BorderStroke(1.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBackground,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        modifier = Modifier,
                        tint = CakkieBrown
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = shop.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
            )
            Text(
                text = shop.description,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                color = CakkieBrown,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn, contentDescription = "",
                    modifier = Modifier.size(16.dp),
                    tint = CakkieBrown
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = shop.address,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = CakkieBrown,
                    modifier = Modifier
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column(Modifier.height(height.minus(88.dp))) {
            //page tabs
            PageTabs(
                pagerState = pageState,
                pageCount = pageState.pageCount,
                tabs = listOf(
                    stringResource(id = R.string.listings),
                    stringResource(id = R.string.requests),
                    stringResource(id = R.string.contracts),
                    stringResource(id = R.string.proposals)
                )
            )
            HorizontalPager(state = pageState) {
                when (it) {
                    2 -> Contracts()
                    3 -> Proposals()
                    1 -> Requests(viewModel, navigator)
                    0 -> Listings(viewModel, navigator)
                }
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
    }
}