package com.cakkie.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.screens.destinations.MyProfileDestination
import com.cakkie.ui.screens.destinations.NotificationDestination
import com.cakkie.ui.screens.destinations.WalletDestination
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun ExploreScreen(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()

    val user = viewModel.user.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = user?.profileImage?.replace("http", "https") ?: "",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(MyProfileDestination)
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Welcome \uD83D\uDC4B",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = user?.name ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.navigate(NotificationDestination) }) {
                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "notification"
                    )
                }

                IconButton(onClick = { navigator.navigate(WalletDestination) }) {
                    Image(
                        painter = painterResource(id = R.drawable.wallet),
                        contentDescription = "wallet"
                    )
                }
            }
        }

        LazyColumn {
            item {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.cakespiration),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.watch_all),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "arrow back",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                }
                LazyRow(
                    Modifier.padding(horizontal = 16.dp)
                ) {
                    items(10) {
                        GlideImage(
                            model = "https://source.unsplash.com/100x150/?cake?video",
                            contentDescription = "cake",
                            modifier = Modifier
                                .width(85.dp)
                                .height(120.dp)
                                .padding(6.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .border(1.dp, CakkieBrown, RoundedCornerShape(8.dp))
                        )
                    }
                }
            }

            items(10) {
                ExploreItem(navigator)
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}