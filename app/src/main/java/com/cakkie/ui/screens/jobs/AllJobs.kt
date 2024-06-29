package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cakkie.R
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.JobResponse
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllJobs(
    jobRes: JobResponse, jobs: SnapshotStateList<JobModel>,
    navigator: DestinationsNavigator, onLoadMore: () -> Unit
) {

    LaunchedEffect(key1 = jobRes.data) {
        if (jobRes.meta.currentPage == 0) {
            jobs.clear()
        }
        jobs.addAll(jobRes.data.filterNot { res ->
            jobs.any { it.id == res.id }
        })
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
//                .pullRefresh(state = state)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (jobs.isEmpty()) {
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                Icon(
                    painter = painterResource(id = R.drawable.jobs),
                    contentDescription = "orders",
                    tint = CakkieLightBrown,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                )
            }
            LazyColumn(Modifier.padding(vertical = 5.dp)) {
                item {
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth(),
////            contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = stringResource(id = R.string.today),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.align(Alignment.CenterStart),
//                    color = Color.Black,
//                    fontSize = 18.sp
//                )
//
//                Spacer(modifier = Modifier.height(5.dp))
//                Image(
//                    painter = painterResource(id = R.drawable.list),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .align(Alignment.CenterEnd)
//                        .padding(20.dp)
//                        .width(24.dp)
//                )
//            }
                }
                items(jobs, key = { it }) { job ->
                    val index = jobs.indexOf(job)
                    if (index > jobs.lastIndex - 2 && jobRes.data.isNotEmpty()) {
                        onLoadMore.invoke()
                    }
                    JobsItems(item = job) {
//                        navigator.navigate("jobDetails") {
//                            launchSingleTop = true
//                        }
                    }

                }
            }
        }
    }
}