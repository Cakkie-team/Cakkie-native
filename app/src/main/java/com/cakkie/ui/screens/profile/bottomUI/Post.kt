package com.cakkie.ui.screens.profile.bottomUI

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.ChangePasswordDestination
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.CommentItemDestination
import com.cakkie.ui.screens.destinations.ContactCakkieDestination
import com.cakkie.ui.screens.destinations.DeleteAccountDestination
import com.cakkie.ui.screens.destinations.PauseNotificationDestination
import com.cakkie.ui.screens.destinations.PostDestination
import com.cakkie.ui.screens.destinations.PostItemDestination
import com.cakkie.ui.screens.destinations.ReportProblemDestination
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.screens.settings.SettingsItemData
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun Post(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val user = viewModel.user.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 15.dp)
    ) {

        Text(
            text = stringResource(id = R.string.post_and_comments),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Text(
            text = stringResource(id = R.string.manage_your_posts_and_comments),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        listOf(
            SettingsItemData(
                image = R.drawable.notification,
                text = R.string.who_can_see_your_post,
            ),
            SettingsItemData(
                image = R.drawable.shield,
                text = R.string.who_can_comment_on_your_posts,
            ),
        ).forEach {
            var expanded by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (it.text) {
                            R.string.who_can_see_your_post -> navigator.navigate(
                                PostItemDestination
                            )

                            R.string.who_can_comment_on_your_posts -> navigator.navigate(
                                CommentItemDestination
                            )

                            else -> {}
                        }
                    }
                    .padding(end = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = it.image),
                        contentDescription = "notification",
                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text = stringResource(id = it.text),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "expand",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(12.dp)
                            .rotate(if (expanded) 90f else 0f)

                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }


}


