package com.cakkie.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.ui.theme.CakkieBrown
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.sign

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageTabs(
    pagerState: PagerState,
    pageCount: Int,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = CakkieBrown,
) {
    val screenSize = LocalConfiguration.current.screenWidthDp.dp / tabs.size
    val indicatorWidthPx = LocalDensity.current.run { screenSize.roundToPx() }
    val spacingPx = LocalDensity.current.run { 10.dp.roundToPx() }
    val scope = rememberCoroutineScope()

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                Box(
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = if (pagerState.currentPage == index) activeColor else {
                            activeColor.copy(alpha = 0.4f)
                        },
                    )
                }
            }
        }
        if (pageCount < 3) {
            Box(
                modifier = Modifier
                    .size(width = screenSize * 2, height = 2.dp)
                    .background(color = activeColor.copy(alpha = 0.4f))
                    .align(Alignment.BottomCenter)
            )
        }
        Box(Modifier
            .offset {
                val position = pageIndexMapping(pagerState.currentPage)
                val offset = pagerState.currentPageOffsetFraction
                val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                val scrollPosition = ((next - position) * offset.absoluteValue + position)
                    .coerceIn(
                        0f,
                        (pageCount - 1)
                            .coerceAtLeast(0)
                            .toFloat()
                    )

                IntOffset(
                    x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt() - (spacingPx * pagerState.currentPage),
                    y = 66
                )
            }
//            .align(Alignment.BottomStart)
            .size(width = screenSize, height = 2.dp)
            .then(
                if (pageCount > 2) Modifier.padding(horizontal = 30.dp)
                else Modifier
            )
            .then(
                if (pageCount > 0) Modifier.background(
                    color = activeColor,
                )
                else Modifier
            ))
    }
}