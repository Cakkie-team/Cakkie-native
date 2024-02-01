package com.cakkie.ui.screens.shop.listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.networkModels.Listing
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.formatDateTime

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListingItem(item: Listing) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = item.media.first(),
                contentDescription = item.name,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(start = 5.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatDateTime(item.createdAt),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = TextColorInactive
                )
            }
        }
        Column(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            Text(
                text = "${item.totalLikes} likes",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                color = CakkieBrown
            )
            Text(
                text = "${item.commentCount} comments",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                color = CakkieBrown
            )
        }
    }
}