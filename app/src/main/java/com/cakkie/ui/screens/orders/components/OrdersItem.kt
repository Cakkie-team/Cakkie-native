package com.cakkie.ui.screens.orders.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.networkModels.Order
import com.cakkie.ui.theme.CakkieBlue
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.ui.theme.Error
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.formatDateTime

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrdersItem(item: Order, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = item.image,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(start = 6.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.createdAt.formatDateTime(),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColorDark.copy(0.7f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .size(width = 96.dp, height = 32.dp),
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = when (item.status.lowercase()) {
                        "pending" -> CakkieYellow
                        "cancelled" -> Error
                        "completed" -> CakkieBlue
                        "declined" -> Error
                        else -> CakkieYellow
                    }
                )
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = item.status.lowercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}