package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Proposal
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProposalItem(
    item: Proposal,
    currencySymbol: String,
    onClick: (() -> Unit)
) {

    Box(
        Modifier
            .clickable { onClick.invoke() }
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.6f),
            ) {
                GlideImage(
                    model = item.shop.image.replace(Regex("\\bhttp://"), "https://"),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(shape = CircleShape)
                        .clickable {

                        },
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(start = 5.dp)) {
                    Text(
                        text = item.shop.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.createdAt.formatDateTime(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = TextColorInactive
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.message,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = TextColorInactive,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                Modifier
                    .padding(end = 10.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.view_application),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = CakkieBrown
                )
                Text(
                    text = formatNumber(item.proposedPrice) + " $currencySymbol",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}