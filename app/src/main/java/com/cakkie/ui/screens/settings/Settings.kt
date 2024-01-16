package com.cakkie.ui.screens.settings

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChangePasswordDestination
import com.cakkie.ui.screens.destinations.ChangeProfileDestination
import com.cakkie.ui.screens.destinations.PauseNotificationDestination
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun Settings(navigator: DestinationsNavigator) {
    val viewModel: ExploreViewModal = koinViewModel()
    val user = viewModel.user.observeAsState().value

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
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
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = user?.profileImage?.replace("http", "https") ?: "",
                contentDescription = "Settings Profile",
                modifier = Modifier
                    .clickable { }
                    .size(width = 60.dp, height = 62.dp)
            )
            Text(
                text = user?.name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(9.dp))

            CakkieButton(
                text = stringResource(id = R.string.edit_profile),
            ) {
                navigator.navigate(ChangeProfileDestination)
            }

        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            listOf(
                SettingsItemData(
                    image = R.drawable.notification,
                    text = R.string.notifications,
                    description = R.string.job_post_posting_followers_following
                ),
                SettingsItemData(
                    image = R.drawable.shield,
                    text = R.string.security,
                    description = R.string.change_password_forgot_password
                ),
                SettingsItemData(
                    image = R.drawable.help_circle,
                    text = R.string.help,
                    description = R.string.help_center_contact_us_terms_privacy_policy_app_nfo
                ),

                SettingsItemData(
                    image = R.drawable.problem,
                    text = R.string.about,
                    description = R.string.about_cakkie_privacy_policy_terms_and_onditions
                ),
            ).forEach {
                var expanded by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(start = 20.dp, end = 14.dp),
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
                                fontSize = 16.sp
                            )
                            Text(
                                text = stringResource(id = it.description),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(0.9f),
                                fontSize = 10.sp,
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

                }
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(expanded) {
                    Box(Modifier.background(color = CakkieLightBrown.copy(alpha = 0.20f))) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        ) {
                            when (it.text) {
                                R.string.notifications -> notificationItem
                                R.string.security -> securityItem
                                R.string.help -> helpItem
                                R.string.about -> aboutItem
                                else -> listOf()
                            }.forEach {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            when (it.text) {
                                                R.string.change_password -> navigator.navigate(
                                                    ChangePasswordDestination
                                                )
                                                
                                                else -> { }
                                            }
                                        },
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
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(text = stringResource(id = it.text))
                                    }


                                    if (it.text == R.string.pause_notification) {
                                        var switchState by remember { mutableStateOf(false) }
                                        Switch(
                                            checked = switchState,
                                            onCheckedChange = { isChecked ->
                                                switchState = isChecked
                                                navigator.navigate(PauseNotificationDestination)
                                            },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = CakkieBrown,
                                                uncheckedThumbColor = CakkieBackground,
                                                uncheckedTrackColor = CakkieBrown.copy(alpha = 0.4f),
                                                checkedTrackColor = CakkieBrown.copy(alpha = 0.5f),
                                            ),
                                        )
                                    } else {
                                        IconButton(onClick = { /*TODO*/ }) {
                                            Image(
                                                painter = painterResource(id = R.drawable.arrow_right),
                                                contentDescription = "expand",
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .clickable { }
                                                    .height(12.dp)
                                            )
                                        }
                                    }

                                }

                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}


val notificationItem = listOf(
    SettingsItemData(
        image = R.drawable.bell,
        text = R.string.pause_notification,
    ),
    SettingsItemData(
        image = R.drawable.post,
        text = R.string.post_and_comments,
    ),
    SettingsItemData(
        image = R.drawable.users,
        text = R.string.following_and_followers,
    ),
    SettingsItemData(
        image = R.drawable.mail,
        text = R.string.email_notifications,
    ),
    SettingsItemData(
        image = R.drawable.message,
        text = R.string.messages,
    ),
    SettingsItemData(
        image = R.drawable.list,
        text = R.string.proposal,
    ),
)
val securityItem = listOf(
    SettingsItemData(
        image = R.drawable.lock,
        text = R.string.change_password,
    )
)
val helpItem = listOf(
    SettingsItemData(
        image = R.drawable.problem,
        text = R.string.report_a_problem,
    ),
    SettingsItemData(
        image = R.drawable.help,
        text = R.string.help_center,
    ),
    SettingsItemData(
        image = R.drawable.contact,
        text = R.string.contact_us,
    )
)

val aboutItem = listOf(
    SettingsItemData(
        image = R.drawable.cakkie,
        text = R.string.about_cakkie,
    ),
    SettingsItemData(
        image = R.drawable.policy,
        text = R.string.privacy_Policy,
    ),
    SettingsItemData(
        image = R.drawable.terms,
        text = R.string.terms_and_conditions,
    )
)

data class SettingsItemData(
    val image: Int,
    val text: Int,
    val description: Int = 0
)

data class SecurityItemData(
    val isExpanded: Boolean,

    )