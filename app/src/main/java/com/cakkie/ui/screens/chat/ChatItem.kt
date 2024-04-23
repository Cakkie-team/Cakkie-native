package com.cakkie.ui.screens.chat

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatItem(item: Int) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp.dp
    val swipeableState = rememberSwipeableState(0)
    // Reset swipeable state after swipe ends
//    LaunchedEffect(swipeableState) {
//        snapshotFlow { swipeableState.offset }
//            .filter {
//                it.value != 0f && swipeableState.currentValue.toFloat() == 1f
//            }
//            .collect {
//                swipeableState.animateTo(0)
//            }
//    }
    Row(
        Modifier
            .swipeable(
                state = swipeableState,
                anchors = mapOf(0f to 0, width.value to 1),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal,
            )
            .fillMaxWidth(),
        horizontalArrangement = if (item % 2 == 0) Arrangement.Start else Arrangement.End
    ) {
        Card(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(
                containerColor = if (item % 2 == 0) CakkieBrown else CakkieBackground,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(Modifier.widthIn(min = 150.dp, max = width * 0.8f)) {
                Spacer(modifier = Modifier.padding(6.dp))
                if (item % 3 == 0) {
                    Card(
                        Modifier
                            .widthIn(min = 150.dp, max = width * 0.8f)
                            .heightIn(min = 43.dp)
                            .padding(horizontal = 12.dp),
                        shape = CardDefaults.elevatedShape,
                        colors = CardDefaults.cardColors(
                            containerColor = if (item % 2 != 0) CakkieBrown else CakkieBackground,
                        ),
                    ) {
                        Text(
                            text = "Chat item $item",
                            color = if (item % 2 != 0) CakkieBackground else TextColorDark,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(10.dp),
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                }
                // Chat item content
                Text(
                    text = "Chat item $item",
                    color = if (item % 2 == 0) CakkieBackground else TextColorDark,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
                Spacer(modifier = Modifier.padding(2.dp))
                // Chat item timestamp
                Text(
                    text = "12:00 PM",
                    color = if (item % 2 == 0) CakkieBackground else TextColorDark,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 10.dp),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}