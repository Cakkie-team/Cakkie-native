package com.cakkie.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.screens.destinations.NotificationDestination
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.screens.destinations.WalletDestination
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
            .padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
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
                            navigator.navigate(ProfileDestination)
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Welcome \uD83D\uDC4B",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = user?.name ?: "",
                        style = MaterialTheme.typography.bodyLarge
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
    }
}