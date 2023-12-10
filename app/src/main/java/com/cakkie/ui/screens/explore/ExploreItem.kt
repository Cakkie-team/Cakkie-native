package com.cakkie.ui.screens.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.HorizontalPagerIndicator
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown

@OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ExploreItem() {
    var maxLines by remember {
        mutableIntStateOf(1)
    }
    var isLiked by remember {
        mutableStateOf(false)
    }
    var isStarred by remember {
        mutableStateOf(false)
    }
    var isSponsored by remember {
        mutableStateOf(true)
    }
    val pageState = rememberPagerState(pageCount = { 3 })
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            Modifier
                .padding(start = 16.dp, bottom = 2.dp, end = 2.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = "https://source.unsplash.com/60x60/?profile",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {

                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Cake Paradise",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isSponsored) stringResource(id = R.string.sponsored) else "8 hours ago",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }


            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.options),
                    contentDescription = "notification"
                )
            }
        }
        if (isSponsored) {
            Row(
                Modifier
                    .background(CakkieBrown)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.view_profile),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
                    color = CakkieBackground
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "arrow back",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .rotate(180f),
                    tint = CakkieBackground
                )
            }
        }
        HorizontalPager(state = pageState) {
            GlideImage(
                model = "https://source.unsplash.com/600x400/?cakes",
                contentDescription = "cake",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.FillBounds
            )
        }
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
                    modifier = Modifier.padding(8.dp)
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
                pageCount = 3,
                modifier = Modifier.offset(x = (-24).dp)
            )
            Card(
                onClick = { },
                modifier = Modifier
                    .width(64.dp)
                    .height(24.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBrown
                )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp),
            color = CakkieBrown
        )

    }
}