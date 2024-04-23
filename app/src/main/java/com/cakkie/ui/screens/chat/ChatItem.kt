package com.cakkie.ui.screens.chat

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieOrange
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ChatItem(item: Int, onReply: () -> Unit) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp.dp
    val density = LocalDensity.current
    val interactionSource = remember { MutableInteractionSource() }
    val dragableState = remember {
        AnchoredDraggableState(
            // 2
            initialValue = DragAnchors.Start,
            // 3
            positionalThreshold = { distance: Float -> distance * 0.5f },
            // 4
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            // 5
            animationSpec = tween()
        ).apply {
            // 6
            updateAnchors(
                // 7
                DraggableAnchors {
                    DragAnchors.Start at 0f
                    DragAnchors.End at if (item % 2 == 0) width.value * 0.8f else -width.value * 0.8f
                }
            )
        }
    }

    //listen to drag end event
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is DragInteraction.Stop -> {
                    onReply.invoke()
                    delay(100)
                    //animate back to start
                    dragableState.animateTo(
                        DragAnchors.Start,
                        velocity = with(density) { 110.dp.toPx() },
                    )
                }
            }
        }
    }


    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (item % 2 == 0) Arrangement.Start else Arrangement.End
    ) {
        Card(
            Modifier
                .anchoredDraggable(
                    state = dragableState,
                    orientation = Orientation.Horizontal,
                    interactionSource = interactionSource,
                )
                .offset {
                    IntOffset(
                        // 2
                        x = dragableState
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(
                containerColor = if (item % 2 != 0) CakkieBrown else CakkieBrown002,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(Modifier.widthIn(min = 150.dp, max = width * 0.8f)) {
                if (item % 3 == 0) {
                    Card(
                        Modifier
                            .width(IntrinsicSize.Max)
                            .heightIn(min = 43.dp),
                        shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                        ),
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "james bond",
                                color = CakkieOrange,
                                style = MaterialTheme.typography.bodyLarge,
//                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Chat item $item",
                                color = CakkieBackground,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 10.dp),
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
                if (item % 2 == 1) {
                    //chat image
                    Card(
                        Modifier
                            .offset(y = (-12).dp),
                        shape = CardDefaults.elevatedShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        GlideImage(
                            model = "https://picsum.photos/200/300",
                            contentDescription = "chat image",
                            modifier = Modifier
                                .height(width * 0.5f)
                                .background(CakkieBrown.copy(alpha = 0.5f)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                // Chat item content
                Text(
                    text = "Chat item $item",
                    color = CakkieBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
                Spacer(modifier = Modifier.padding(2.dp))
                // Chat item timestamp
                Text(
                    text = "12:00 PM",
                    color = CakkieBackground,
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

enum class DragAnchors {
    Start,
    End,
}