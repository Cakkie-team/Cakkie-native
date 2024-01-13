package com.cakkie.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun ChangeProfile(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val user = viewModel.user.observeAsState().value

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    }
                    .size(24.dp)
            )

            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = user?.profileImage?.replace("http", "https") ?: "",
                contentDescription = "Settings Profile",
                modifier = Modifier
                    .clickable { }
                    .size(100.dp)
            )
            Text(
                text = user?.name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(9.dp))

            CakkieButton(
                text = stringResource(id = R.string.change_profile_picture)
            ) {
            }

        }


    }
}
