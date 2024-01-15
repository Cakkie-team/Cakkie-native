package com.cakkie.ui.screens.cakespiration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CakespirationItem() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = Modifier
            .background(Color.Black)
            .height(screenHeight - 42.dp),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = "https://source.unsplash.com/400x750/?cake?video",
            contentDescription = "cake video",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}