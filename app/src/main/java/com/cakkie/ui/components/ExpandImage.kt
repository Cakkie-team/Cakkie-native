package com.cakkie.ui.components

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.data.db.models.Listing
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.ItemDetailsDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ExpandImage(
    item: Listing,
    expanded: Boolean,
    onDismiss: () -> Unit,
    pageState: PagerState = rememberPagerState(pageCount = { 1 }),
    showDetails: Boolean = false,
    navigator: DestinationsNavigator
) {
    var maxLines by rememberSaveable { mutableIntStateOf(1) }
    var isLiked by rememberSaveable { mutableStateOf(item.isLiked) }
    var isStarred by rememberSaveable { mutableStateOf(item.isStarred) }

    //close on back press
    BackHandler(expanded) {
        onDismiss.invoke()
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
                HorizontalPager(state = pageState) {
                    GlideImage(
                        imageModel = item.media[it],
                        contentDescription = "cake",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        shimmerParams = ShimmerParams(
                            baseColor = CakkieBrown.copy(0.4f),
                            highlightColor = CakkieBrown.copy(0.8f),
                            dropOff = 0.55f,
                            tilt = 20f
                        )
                    )
                }
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
                            tint = CakkieBrown,
                            modifier = Modifier.width(24.dp)
                        )
                    }
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
                                        .width(24.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.comment),
                                    contentDescription = "comment",
                                    modifier = Modifier
                                        .clickable {
                                            onDismiss.invoke()
                                            navigator.navigate(CommentDestination(item = item))
                                        }
                                        .padding(8.dp)
                                        .width(24.dp)
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
                                        .width(24.dp)
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
                                onClick = {
                                    navigator.navigate(
                                        ItemDetailsDestination(
                                            id = item.id,
                                            item = item
                                        )
                                    )
                                },
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
                            text = "${item.totalLikes} Likes",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp),
                            color = CakkieBrown
                        )
                        Text(
                            text = item.description,
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
                            text = "View all ${item.commentCount} comments",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .clickable {
                                    onDismiss.invoke()
                                    navigator.navigate(CommentDestination(item = item))
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