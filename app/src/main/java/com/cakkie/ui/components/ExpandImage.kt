package com.cakkie.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.ItemDetailsDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ExpandImage(
    expanded: Boolean,
    onDismiss: () -> Unit,
    pageState: PagerState = rememberPagerState(pageCount = { 1 }),
    showDetails: Boolean = false,
    navigator: DestinationsNavigator
) {
    var maxLines by remember {
        mutableIntStateOf(1)
    }
    var isLiked by remember {
        mutableStateOf(false)
    }
    var isStarred by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = expanded) {
        var clear by remember {
            mutableStateOf(false)
        }
        Popup {
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable { clear = !clear }
                    .draggable(
                        state = rememberDraggableState(onDelta = {}),
                        orientation = Orientation.Vertical,
                        onDragStopped = {
                            onDismiss.invoke()
                        })
                    .background(CakkieBackground),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = !clear, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    IconButton(
                        onClick = { onDismiss.invoke() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Cancel",
                            tint = CakkieBrown
                        )
                    }
                }
                HorizontalPager(state = pageState) {
                    GlideImage(
                        model = "https://source.unsplash.com/600x400/?cakes",
                        contentDescription = "cake",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }
                AnimatedVisibility(
                    visible = !clear && showDetails, Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(CakkieBackground.copy(alpha = 0.3f))
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            Modifier
                                .padding(start = 8.dp, end = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(
                                        id =
                                        if (isLiked) R.drawable.gridicons_heart else R.drawable.heart
                                    ),
                                    contentDescription = "heart",
                                    modifier = Modifier
                                        .clickable {
                                            isLiked = !isLiked
                                        }
                                        .padding(8.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.comment),
                                    contentDescription = "comment",
                                    modifier = Modifier
                                        .clickable {
                                            onDismiss.invoke()
                                            navigator.navigate(CommentDestination)
                                        }
                                        .padding(8.dp)
                                )
                                Image(
                                    painter = painterResource(
                                        id =
                                        if (isStarred) R.drawable.star_filled else R.drawable.star_add
                                    ),
                                    contentDescription = "star",
                                    modifier = Modifier
                                        .clickable {
                                            isStarred = !isStarred
                                        }
                                        .padding(8.dp)
                                )
                            }
                            HorizontalPagerIndicator(
                                pagerState = pageState,
                                activeColor = CakkieBrown,
                                spacing = 8.dp,
                                indicatorWidth = 5.dp,
                                indicatorHeight = 5.dp,
                                pageCount = pageState.pageCount,
                                modifier = Modifier.offset(x = (-24).dp)
                            )
                            Card(
                                onClick = { navigator.navigate(ItemDetailsDestination) },
                                modifier = Modifier
                                    .width(64.dp)
                                    .height(24.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = CakkieBrown
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.order),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CakkieBackground
                                    )
                                }
                            }
                        }
                        Text(
                            text = "400 Likes",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp),
                            color = CakkieBrown
                        )
                        Text(
                            text = "Indulge in the best cakes in town with Cake Paradise and get 10% off on your first order!",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp
                            ),
                            maxLines = maxLines,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (maxLines == 1) {
                            Text(
                                text = stringResource(id = R.string.see_more),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .clickable { maxLines = Int.MAX_VALUE }
                                    .padding(start = 16.dp),
                                color = CakkieBrown
                            )
                        }
                        Text(
                            text = "View all 20 comments",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .clickable {
                                    onDismiss.invoke()
                                    navigator.navigate(CommentDestination)
                                }
                                .padding(start = 16.dp, end = 16.dp, top = 5.dp),
                            color = CakkieBrown
                        )
                    }
                }
            }
        }
    }
}