package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cakkie.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllJobs(
//    listings: ListingResponse, post: SnapshotStateList<Listing>,
//    navigator: DestinationsNavigator, onLoadMore: () -> Unit
) {
    val jobs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    LazyColumn(Modifier.padding(vertical = 5.dp)) {
        item {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
//            contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.today),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(20.dp)
                        .width(24.dp)
                )
            }
        }
        items(jobs, key = { it }) { job ->
//            val index = post.indexOf(listing)
//            if (index > post.lastIndex - 2 && listings.data.isNotEmpty()) {
//                onLoadMore.invoke()
//
//            }

            JobsItems(item = "")

        }
    }
}