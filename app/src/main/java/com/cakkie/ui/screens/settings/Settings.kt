package com.cakkie.ui.screens.settings

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Switch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChangeProfileDestination
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun Settings(navigator: DestinationsNavigator) {
    val sheetState = rememberModalBottomSheetState()
    var isSwitchOn by rememberSaveable { mutableStateOf(false) }
    var switchOn by remember { mutableStateOf(false) }
    var processing by remember {
        mutableStateOf(false)
    }
    val viewModel: ExploreViewModal = koinViewModel()
    val user = viewModel.user.observeAsState().value

    val radioButtons = remember {
        mutableStateListOf(
            Toggledinfo(
                isChecked = true, text = "6 hours"
            ),
            Toggledinfo(
                isChecked = false, text = "24 hours"
            ),
            Toggledinfo(
                isChecked = false, text = "1 week"
            ),
            Toggledinfo(
                isChecked = false, text = "1 Month"
            ),
            Toggledinfo(
                isChecked = false, text = "Till Further Notice"
            ),

            )
    }

    var switch by remember {
        mutableStateOf(
            Toggledinfo(
                isChecked = false, text = "Pause Notification"
            ),
        )
    }

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
            )

            Spacer(Modifier.height(9.dp))

            CakkieButton(
                text = stringResource(id = R.string.edit_profile),
                processing = processing,
                modifier = Modifier.clickable {
                    navigator.navigate(ChangeProfileDestination)
                }
            ) {
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
                    description = R.string.change_password_forgot_assword
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
                        .padding(16.dp),
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
                                modifier = Modifier,
                                fontSize = 10.sp
                            )
                        }
                    }
                    IconButton(onClick = { expanded = !expanded }) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right),
                            contentDescription = "expand",
                            Modifier
                                .size(20.dp)
                                .rotate(if (expanded) 90f else 0f)
                        )
                    }
                }

                if (expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = CakkieLightBrown.copy(alpha = 0.10f))

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(id = R.drawable.notification),
                                    contentDescription = "notification",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(text = switch.text)
                                Spacer(modifier = Modifier.weight(1f))
                                Switch(checked = switch.isChecked, onCheckedChange = { isChecked ->
                                    switch = switch.copy(isChecked = isChecked)
                                })
                                Text(
                                    text = stringResource(id = R.string.pause_notification),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 16.sp
                                )

                            }
                        }

                        Image(
                            painter = if (switchOn) painterResource(id = R.drawable.switch_on) else painterResource(
                                id = R.drawable.switch_
                            ),
                            contentDescription = "expand",
                            modifier = Modifier
                                .size(height = 24.dp, width = 24.dp)
                                .clickable {
                                    isSwitchOn = true
                                }
                        )
                        if (isSwitchOn) {
                            ModalBottomSheet(
                                sheetState = sheetState,
                                onDismissRequest = { isSwitchOn = false },
                                containerColor = CakkieBackground
                            )

                            {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                ) {

                                    Text(
                                        text = stringResource(id = R.string.pause_notification),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = stringResource(id = R.string.pause_notification_message),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier,
                                        fontSize = 10.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(color = Color.Unspecified)
                                        ) {
                                            radioButtons.forEachIndexed { index, info ->
                                                Row(verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.clickable {
                                                        radioButtons.replaceAll {
                                                            it.copy(
                                                                isChecked = it.text == info.text
                                                            )
                                                        }
                                                    }
                                                ) {
                                                    RadioButton(
                                                        selected = info.isChecked,
                                                        onClick = {
                                                            radioButtons[index] = info.copy(
                                                                isChecked = info.isChecked
                                                            )
                                                        },

                                                        )
                                                    Text(text = info.text)
                                                }
                                            }
                                        }

                                    }
                                    Spacer(modifier = Modifier.height(15.dp))

                                    CakkieButton(
                                        Modifier.height(50.dp),
                                        processing = processing,
                                        text = stringResource(id = R.string.done)
                                    ) {
                                    }
                                    Spacer(modifier = Modifier.height(50.dp))

                                }


                            }
                        }
                    }
                }
            }

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.message_),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.comment),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.users),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.following_and_followers),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.mail),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.email_notifications),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.message),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.messages),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.list),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.proposal),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//            }
//
//
//        }
        }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painterResource(id = R.drawable.shield),
//                contentDescription = "notification",
//                modifier = Modifier.size(height = 24.dp, width = 24.dp)
//            )
//            Column(
//                modifier = Modifier.padding(horizontal = 10.dp)
//            ) {
//                Text(
//                    text = stringResource(id = R.string.security),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontWeight = FontWeight.W400,
//                    fontSize = 16.sp
//                )
//
//                Text(
//                    text = stringResource(id = R.string.change_password_forgot_assword),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontSize = 10.sp
//                )
//
//            }
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//
//        IconButton(onClick = { isExpanded = !isExpanded }) {
//
//            Image(
//                painter = if (isExpanded) painterResource(id = R.drawable.arrow_down) else painterResource(
//                    id = R.drawable.expand
//                ),
//                contentDescription = "expand",
//                Modifier.size(20.dp)
//            )
//        }
//    }
//
//    if (isExpanded) {
//        SecurityItem(navigator = navigator, email = "")
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Row(
//
//        ) {
//            Image(
//                painterResource(id = R.drawable.problem),
//                contentDescription = "notification",
//                modifier = Modifier.size(height = 24.dp, width = 24.dp)
//            )
//            Column(
//                modifier = Modifier.padding(horizontal = 10.dp)
//            ) {
//                Text(
//                    text = stringResource(id = R.string.help),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontWeight = FontWeight.W400,
//                    fontSize = 16.sp
//                )
//
//                Text(
//                    text = stringResource(id = R.string.help_center_contact_us_terms_privacy_policy_app_nfo),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontSize = 10.sp
//
//                )
//
//            }
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        IconButton(onClick = { itemExpanded = !itemExpanded }) {
//
//            Image(
//                painter = if (itemExpanded) painterResource(id = R.drawable.arrow_down) else painterResource(
//                    id = R.drawable.expand
//                ),
//                contentDescription = "expand",
//                Modifier.size(20.dp)
//            )
//        }
//    }
//
//    if (itemExpanded) {
//        HelpItems(navigator = navigator)
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Row(
//
//        ) {
//            Image(
//                painterResource(id = R.drawable.info),
//                contentDescription = "notification",
//                modifier = Modifier.size(height = 24.dp, width = 24.dp)
//            )
//            Column(
//                modifier = Modifier.padding(horizontal = 10.dp)
//            ) {
//                Text(
//                    text = stringResource(id = R.string.about),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontWeight = FontWeight.W400,
//                    fontSize = 16.sp
//                )
//
//                Text(
//                    text = stringResource(id = R.string.about_cakkie_privacy_policy_terms_and_onditions),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier,
//                    fontSize = 10.sp
//                )
//
//            }
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        IconButton(onClick = { isItemExpanded = !isItemExpanded }) {
//
//            Image(
//                painter = if (isItemExpanded) painterResource(id = R.drawable.arrow_down) else painterResource(
//                    id = R.drawable.expand
//                ),
//                contentDescription = "expand",
//                Modifier.size(20.dp)
//            )
//        }
//    }
//    if (isItemExpanded) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = CakkieLightBrown)
//
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.cakkie),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.about_cakkie),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.policy),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.privacy_Policy),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.terms),
//                        contentDescription = "notification",
//                        modifier = Modifier.size(height = 24.dp, width = 24.dp)
//                    )
//                    Column(
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.terms_and_conditions),
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                }
//
//                Image(
//                    painterResource(id = R.drawable.arrow_right),
//                    contentDescription = "expand",
//                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
//
//
//                )
//            }
//            Spacer(modifier = Modifier.height(38.dp))
//
//        }
//    }
    }
}


data class Toggledinfo(
    val isChecked: Boolean,
    val text: String
)


data class SettingsItemData(
    val image: Int,
    val text: Int,
    val description: Int
)