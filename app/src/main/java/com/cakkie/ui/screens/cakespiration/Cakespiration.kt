package com.cakkie.ui.screens.cakespiration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Cakespiration(index: Int = 0, navigator: DestinationsNavigator) {
    val listState = rememberLazyListState(index)
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillParentMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CakespirationItem(
                        navigator,
                        remember {
                            derivedStateOf { listState.firstVisibleItemIndex }
                        }.value == it - 1 || remember {
                            derivedStateOf {
                                !listState.canScrollBackward || !listState.canScrollForward
                            }
                        }.value
                    )
                }
            }
        }

        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navigator.popBackStack() },
                Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.cakespiation),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                color = CakkieBackground
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.size(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(
                        id = R.string.search
                    ),
                    tint = CakkieBackground
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = CakkieBackground
                )
                Spacer(modifier = Modifier.size(1.dp))
                Divider(
                    Modifier
                        .size(2.dp)
                        .clip(CircleShape), color = CakkieBackground
                )
                Spacer(modifier = Modifier.size(1.dp))
                Text(
                    text = "Chocolate cake",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = CakkieBackground
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    tint = CakkieBackground,
                    modifier = Modifier
                        .rotate(180f)
                        .width(24.dp),
                )
            }
        }
    }
}