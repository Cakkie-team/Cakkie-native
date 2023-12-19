package com.cakkie.ui.screens.notification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun Notification(navigator: DestinationsNavigator) {
    val viewModel: NotificationViewModel = koinViewModel()

    val showTip = viewModel.isNotificationTipShown.collectAsState(true).value
    val itemList =
        mutableListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7")

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
                text = stringResource(id = R.string.notification),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(
            state = rememberLazyListState()
        ) {
            items(7) { index ->
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
//                            itemList.removeAt(index)
                        }
                        true
                    }
                )
                SwipeToDismiss(state = state, background =
                {
                    NotificationItem(isBackground = true, navigator = navigator)

                }, dismissContent =
                {
                    NotificationItem(isBackground = false, navigator = navigator)
                })
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }

    AnimatedVisibility(visible = !showTip) {
        Popup(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, 100)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(TextColorDark, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .height(70.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = "Cancel",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            viewModel.setNotificationTipShown()
                        }
                        .padding(end = 12.dp, top = 12.dp)
                        .size(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.swipe_a_notification_to_delete_it),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBackground,
//               fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}

