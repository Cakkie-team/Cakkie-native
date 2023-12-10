package com.cakkie.ui.screens.explore.bottomUi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@OptIn(ExperimentalGlideComposeApi::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun Comment() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .width(72.dp)
                .align(Alignment.CenterHorizontally),
            color = CakkieBrown,
            thickness = 8.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(10) {
                CommentItem()
            }
        }
    }
}