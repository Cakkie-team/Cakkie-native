package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Proposal
import com.cakkie.networkModels.ProposalResponse
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.screens.explore.Reviews
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun ProposalDetails(
    id: String,
    currencySymbol: String,
    item: Proposal?,
    navigator: DestinationsNavigator
) {

    val viewModel: JobsViewModel = koinViewModel()
    val proposalsRes = viewModel.proposals.observeAsState(ProposalResponse()).value
    val proposals = remember {
        mutableStateListOf<Proposal>()
    }
    var jobId by remember {
        mutableStateOf(item?.id)
    }
    val config = LocalConfiguration.current
    val width = config.screenWidthDp.dp
    var loading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = jobId) {
        if (jobId != null) {
            loading = true
            viewModel.getProposals(jobId!!)
        }
    }
    val listState =
        rememberLazyListState(0)
    val index by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val currentItem by remember {
        derivedStateOf {
            if (proposals.size > 0) {
                proposals[
                    if (index > proposals.size) proposals.size - 1 else index
                ]
            } else {
                item ?: Proposal()
            }
        }
    }
    LaunchedEffect(key1 = proposalsRes?.data) {
        loading = false
//        if (proposalsRes.meta.currentPage == 0) {
//            proposals.clear()
//        }
        proposals.addAll(proposalsRes.data.filterNot { res ->
            proposals.any { it.id == res.id }
        })

    }

    LaunchedEffect(key1 = id) {
        if (proposals.find { it.id == id } != null) {
            listState.scrollToItem(proposals.indexOf(proposals.find { it.id == id }))
            jobId = proposals.find { it.id == id }?.jobId
        } else {
            if (item == null) {
                viewModel.getProposal(id).addOnSuccessListener {
                    proposals.add(0, it)
                    jobId = it.jobId
                }.addOnFailureListener {
                    Toaster(
                        context,
                        it.localizedMessage ?: "Something went wrong",
                        R.drawable.logo
                    )
                }
            } else {
                proposals.add(0, item)
                jobId = item.jobId
            }
            delay(500)
            listState.scrollToItem(0)
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {
//        Spacer(modifier = Modifier.height(20.dp))
        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }

            Text(
                stringResource(id = R.string.proposals),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.padding(start = 20.dp)) {
            GlideImage(
                model = currentItem.shop.image.ifEmpty {
                    "https://cdn.cakkie.com/imgs/Cakkie%20Icon%20(6).png"
                }.replace(Regex("\\bhttp://"), "https://"),
                contentDescription = "profile pic",
                modifier = Modifier
                    .size(30.dp)
                    .clip(shape = CircleShape)
//                .padding(20.dp)
                    .clickable {

                    },
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = currentItem.shop.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = CakkieBrown
                )
                Text(
                    text = currentItem.createdAt.formatDateTime(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = TextColorInactive
                )
            }

            Row {
                IconButton(
                    onClick = { /*TODO*/ },
                    enabled = index > 0,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Prev",
                        modifier = Modifier
                            .size(24.dp),
                        contentScale = ContentScale.FillWidth,
                        alpha = if (index > 0) 1f else 0.5f
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    enabled = index < proposals.size - 1
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Next",
                        modifier = Modifier
                            .rotate(180f)
                            .size(24.dp),
                        contentScale = ContentScale.FillWidth,
                        alpha = if (index < proposals.size - 1) 1f else 0.5f
                    )
                }
            }
        }


        LazyRow(
            Modifier.padding(horizontal = 16.dp),
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            items(items = proposals, key = { it.id }) { prop ->
                val indx = proposals.indexOf(prop)
                if (indx > proposals.lastIndex - 2 && proposalsRes?.meta?.nextPage != null && jobId != null) {
                    viewModel.getProposals(
                        jobId!!,
                        proposalsRes.meta.nextPage,
                        proposalsRes.meta.pageSize
                    )
                }
                Column(Modifier.width(width - 32.dp)) {
                    Text(
                        text = prop.message,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
//                        color = TextColorInactive
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    listOf(
                        R.string.proposed_price,
                        R.string.proposed_completion,
//                        R.string.location,
                    ).forEach {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = it),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                                color = CakkieBrown,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = when (it) {
                                    R.string.proposed_price -> formatNumber(prop.proposedPrice) + " $currencySymbol"
                                    R.string.proposed_completion -> prop.proposedDeadline.formatDateTime()
//                                    R.string.location -> prop.
                                    else -> ""
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                                color = CakkieBrown
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    CakkieButton(
                        text = stringResource(id = R.string.send_a_message),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally),
                        enabled = prop.status == "PENDING" || prop.status == "AWARDED"
                    ) {
                        navigator.navigate(
                            ChatDestination(
                                prop.id,
                                idIsProposal = true,
                                shopId = prop.shopId
                            )
                        ) {
                            launchSingleTop = true
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.reviews),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        color = CakkieBrown,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Reviews(prop.shopId)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}