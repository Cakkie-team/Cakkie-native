package com.cakkie.ui.screens.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBlue
import com.cakkie.ui.theme.TextColorDark

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrdersItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = "https://source.unsplash.com/100x100/?cakes,cover",
                contentDescription = "",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(start = 6.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.velvet_cupcakes),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "12 May, 8:23 am",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColorDark.copy(0.7f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Card(
                onClick = {},
                modifier = Modifier
                    .size(width = 96.dp, height = 32.dp),
                shape = CardDefaults.elevatedShape,
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBlue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.pending),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
            }
        }
    }
}