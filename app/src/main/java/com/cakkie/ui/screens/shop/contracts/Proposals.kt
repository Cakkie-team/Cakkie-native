package com.cakkie.ui.screens.shop.contracts

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Proposal
import com.cakkie.networkModels.ProposalResponse
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.PropDetailDestination
import com.cakkie.ui.theme.CakkieBlue
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.ui.theme.Error
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.formatDateTime
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun Proposals(
    proposalRes: ProposalResponse,
    proposals: SnapshotStateList<Proposal>,
    navigator: DestinationsNavigator, onLoadMore: () -> Unit
) {
    LaunchedEffect(key1 = proposalRes.data) {
        if (proposalRes.meta.currentPage == 0) {
            proposals.clear()
        }
        proposals.addAll(proposalRes.data.filterNot { res ->
            proposals.any { it.id == res.id }
        })
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (proposals.isEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CakkieBrown,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.get_started_now_by_checking_out_our_available_jobs),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColorInactive,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                CakkieButton(
                    text = stringResource(id = R.string.check_jobs),
                    modifier = Modifier
//                .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                ) {


                }
            }
        } else {
            LazyColumn(Modifier.padding(vertical = 5.dp)) {
                items(proposals, key = { it.id }) { proposal ->
                    val index = proposals.indexOf(proposal)
                    if (index > proposals.lastIndex - 2 && proposalRes.data.isNotEmpty()) {
                        onLoadMore.invoke()
                    }
                    ProposalItem(proposal) {
                        navigator.navigate(PropDetailDestination(proposal.id, proposal))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProposalItem(item: Proposal, onClick: () -> Unit) {
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
                model = item.media.firstOrNull() ?: "",
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 6.dp),
                verticalArrangement = Arrangement.Top
            ) {
                androidx.compose.material3.Text(
                    text = "Proposal to ${item.job.user.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                androidx.compose.material3.Text(
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
                        "pending" -> CakkieBlue
                        "cancelled" -> Error
                        "awarded" -> CakkieGreen
                        "declined" -> CakkieYellow
                        else -> CakkieYellow
                    }
                )
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Text(
                        text = item.status.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}