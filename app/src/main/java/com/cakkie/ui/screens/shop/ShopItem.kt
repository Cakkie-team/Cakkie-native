package com.cakkie.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.data.db.models.ShopModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun ShopItem(item: ShopModel, onClick: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
            .clickable { onClick.invoke() },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "Image",
                onState = { isLoading = it is AsyncImagePainter.State.Loading },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = isLoading,
                        color = CakkieBrown.copy(0.8f),
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.city,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = TextColorInactive
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun AllTabShopItem(item: ShopModel, onClick: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "Image",
                onState = { isLoading = it is AsyncImagePainter.State.Loading },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = isLoading,
                        color = CakkieBrown.copy(0.8f),
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.city,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = TextColorInactive
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
