package com.cakkie.ui.screens.jobs.bottomUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.networkModels.Proposal
import com.cakkie.networkModels.ProposalResponse
import com.cakkie.ui.screens.destinations.ProposalDetailsDestination
import com.cakkie.ui.screens.jobs.JobsViewModel
import com.cakkie.ui.screens.jobs.ProposalItem
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun Proposals(
    id: String,
    symbol: String,
    navigator: DestinationsNavigator
) {
    val viewModel: JobsViewModel = koinViewModel()
    val proposalsRes = viewModel.proposals.observeAsState(ProposalResponse()).value
    val proposals = remember {
        mutableStateListOf<Proposal>()
    }

    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = id) {
        loading = true
        viewModel.getProposals(id)
    }

    LaunchedEffect(key1 = proposalsRes?.data) {
        loading = false
        if (proposalsRes.meta.currentPage == 0) {
            proposals.clear()
        }
        proposals.addAll(proposalsRes.data.filterNot { res ->
            proposals.any { it.id == res.id }
        })

    }
    Column(
        modifier = Modifier
//            .padding(horizontal = 16.dp)
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
        Column(
            modifier = Modifier
//                .pullRefresh(state = state)
                .heightIn(min = 400.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (proposals.isEmpty()) {
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
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
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
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
                items(proposals, key = { it }) { proposal ->
                    val index = proposals.indexOf(proposal)
                    if (index > proposals.lastIndex - 2 && proposalsRes.data.isNotEmpty()) {
                        viewModel.getProposals(id, proposalsRes.meta.nextPage)
                    }
                    ProposalItem(item = proposal, symbol) {
                        navigator.navigate(
                            ProposalDetailsDestination(
                                proposal.id,
                                symbol,
                                proposal
                            )
                        ) {
                            launchSingleTop = true
                        }
                    }

                }

                //add a loading indicator
                if (loading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp),
                                color = CakkieBrown,
                                strokeWidth = 2.dp,
                                trackColor = CakkieBrown.copy(alpha = 0.4f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                    }
                }
            }
        }
    }
}