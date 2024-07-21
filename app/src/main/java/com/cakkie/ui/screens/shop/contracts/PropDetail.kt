package com.cakkie.ui.screens.shop.contracts

import android.os.Build
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.Proposal
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.screens.orders.OrderViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber
import com.cakkie.utill.isVideoUrl
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun PropDetail(
    id: String,
    item: Proposal = Proposal(),
    navigator: DestinationsNavigator
) {
    val viewModel: OrderViewModel = koinViewModel()
    var proposal by remember {
        mutableStateOf(item)
    }
    val context = LocalContext.current
    val meta = listOf(
        Pair("Proposed Completion Date", proposal.proposedDeadline.formatDateTime()),
        Pair("Proposed Price", formatNumber(proposal.proposedPrice) + " NGN"),
        Pair("Status", proposal.status.lowercase().replaceFirstChar { it.uppercase() }),
//        Pair("Quantity", proposal.quantity.toString()),
    )


    LaunchedEffect(id) {
        viewModel.getProposal(id)
            .addOnSuccessListener {
                proposal = it
            }
            .addOnFailureListener {
                Toaster(
                    context,
                    it.localizedMessage ?: "Unable to retrive order",
                    R.drawable.logo
                ).show()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    },
            )
            Text(
                text = stringResource(id = R.string.proposal),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = proposal.job.user.profileImage.replace(Regex("\\bhttp://"), "https://"),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row {
                        Text(
                            text = stringResource(id = R.string.proposal_to),
                            style = MaterialTheme.typography.bodyLarge,

                            )
                        Text(
                            text = proposal.job.user.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                    }
                    Text(
                        text = "on ${proposal.createdAt.formatDateTime()}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColorDark.copy(alpha = 0.7f)
                    )
                }
            }
            if (false) {
                IconButton(
                    onClick = {
                        navigator.navigate(ChatDestination(proposal.id))
                    }, modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = stringResource(
                            id = R.string.chat
                        ),
                        modifier = Modifier
                            .size(27.dp),
                        tint = TextColorDark
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .heightIn(max = 180.dp)
//                    .clip(MaterialTheme.shapes.medium)
//                    .padding(horizontal = 5.dp)
                    .background(Color.White.copy(alpha = 0.6f))
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = proposal.media.firstOrNull() ?: "",
                    contentDescription = "Media",
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(
                            radius = if ((proposal.media.firstOrNull()
                                    ?: "").isVideoUrl()
                            ) 10.dp else 0.dp
                        ),
                    contentScale = ContentScale.Crop,
                )
                if ((proposal.media.firstOrNull() ?: "").isVideoUrl()) {
                    val exoPlayer = remember {
                        ExoPlayer.Builder(context).build().apply {
                            val defaultDataSourceFactory =
                                DefaultDataSource.Factory(context)
                            val dataSourceFactory: DataSource.Factory =
                                DefaultDataSource.Factory(
                                    context, defaultDataSourceFactory
                                )
                            val source =
                                ProgressiveMediaSource.Factory(dataSourceFactory)
                                    .createMediaSource(
                                        MediaItem.fromUri(
                                            (proposal.media.firstOrNull() ?: "").toUri()
                                        )
                                    )
                            setMediaSource(source)
                            prepare()
                        }
                    }
                    exoPlayer.playWhenReady = false
                    exoPlayer.videoScalingMode =
                        C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                    AndroidView(factory = {
                        PlayerView(context).apply {
                            hideController()
                            useController = true
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                            player = exoPlayer
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    })
                    DisposableEffect(Unit) {
                        onDispose { exoPlayer.release() }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = proposal.message,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))

                meta.filterIndexed { index, _ -> index % 2 == 0 }
                    .zip(meta.filterIndexed { index, _ -> index % 2 != 0 }).forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = it.first.first,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBrown
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = it.first.second,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = it.second.first,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = CakkieBrown
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = it.second.second,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }

                /* Spacer(modifier = Modifier.height(5.dp))
                 Column {
                     Text(
                         text = stringResource(id = R.string.delivery_address),
                         style = MaterialTheme.typography.bodyLarge,
                         color = CakkieBrown
                     )
                     Spacer(modifier = Modifier.height(10.dp))
                     Row(verticalAlignment = Alignment.CenterVertically) {
                         Image(
                             painter = painterResource(
                                 id = R.drawable.location
                             ),
                             contentDescription = "location",
                             modifier = Modifier.size(24.dp)
                         )
                         Text(
                             text = order.deliveryAddress,
                             style = MaterialTheme.typography.bodyLarge,
                         )
                     }
                 }*/
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        Text(
                            text = stringResource(id = R.string.status),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Icon(
                                painter = painterResource(
                                    id = when (proposal.status.lowercase()) {
                                        "pending" -> R.drawable.done
                                        "cancelled" -> R.drawable.cancel
                                        "declined" -> R.drawable.cancel
                                        "inprogress" -> R.drawable.progress
                                        else -> R.drawable.approved
                                    }
                                ),
                                contentDescription = "",
                                tint = CakkieBrown
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = proposal.status.lowercase(),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = stringResource(
                                id = R.string.location
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = proposal.job.city,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}