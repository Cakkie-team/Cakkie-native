package com.cakkie.ui.screens.jobs

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.networkModels.FileModel
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber
import com.cakkie.utill.isVideoUrl
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.util.Locale

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun JobDetails(
    id: String,
    item: JobModel = JobModel(),
    files: String = "",
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    val viewModel: JobsViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    var job by rememberSaveable {
        mutableStateOf(item)
    }
    //convert string to list of media
    val media = files.toObjectList(MediaModel::class.java)
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var maxLines by rememberSaveable { mutableIntStateOf(2) }

//    var isMuted by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(key1 = id) {
        viewModel.getJob(id).addOnSuccessListener {
            job = it
        }.addOnFailureListener {
            Toaster(
                context, it.localizedMessage ?: "Something went wrong", R.drawable.logo
            )
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
                stringResource(id = R.string.job_description),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.Center)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Row {
                    Text(
                        text = stringResource(id = R.string.job_by),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = " ${job.user.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = "on ${job.createdAt.formatDateTime()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp
                )
            }

            Text(
                text = "Applications: ${job.totalProposal} - ${job.totalProposal.plus(2)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = job.title,
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {
                items(items = media.ifEmpty {
                    job.media.map {
                        MediaModel(
                            uri = it,
                            dateAdded = System.currentTimeMillis(),
                            isVideo = it.isVideoUrl(),
                            mediaMimeType = it.substringAfterLast("."),
                            name = it.substringAfterLast("/"),
                        )

                    }
                }) { media ->
                    Box(
                        modifier = Modifier
                            .height(169.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .padding(horizontal = 8.dp)
                            .background(Color.White.copy(alpha = 0.6f))
                            .width(screenWidth * if (job.media.size > 1) 0.9f else 1f)
                    ) {
                        GlideImage(
                            model = media.uri.toUri(),
                            contentDescription = "Media",
                            modifier = Modifier
                                .height(169.dp)
                                .blur(radius = 10.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                        if (media.isVideo) {
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
                                            .createMediaSource(MediaItem.fromUri(media.uri.toUri()))
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
                        } else {
                            GlideImage(
                                model = media.uri.toUri(),
                                contentDescription = "Media",
                                modifier = Modifier
                                    .height(169.dp)
                                    .clickable {
                                        navigator.navigate(ChooseMediaDestination(from = "job"))
                                    }
                                    .clip(MaterialTheme.shapes.medium)
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
            if (maxLines == 2) {
                Text(
                    text = stringResource(id = R.string.see_more),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable { maxLines = Int.MAX_VALUE },
                    color = CakkieBrown
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                job.meta.toListOfPairs().forEach {
                    Row(
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.first,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Text(
                            text = it.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.completion_date),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = job.deadline.formatDateTime(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.proposed_price),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = job.currencySymbol + formatNumber(job.salary),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.location),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = job.city,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.status),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Row {
                        Text(
                            text = if (job.hasApplied) stringResource(
                                id = R.string.applied
                            ) else stringResource(
                                id = R.string.you_have_not_appl
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.widthIn(max = 150.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(
                                id = if (job.hasApplied) R.drawable.approved else R.drawable.not
                            ),
                            contentDescription = "not",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.proposal_fee),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = formatNumber(job.proposalFee),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            if (job.hasEnoughBalance.not()) {
                Column(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.insuficint_balance, job.proposalFee),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Error,
                        textAlign = TextAlign.Center
                    )
                    Text(text = stringResource(id = R.string.add_more_icing),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
//                            navigator.navigate(ChooseMediaDestination(from = "job"))
                            }
                    )

                }
            }

            if (media.size > 0) {
                CakkieButton(
                    text = stringResource(id = R.string.apply_now),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f),
                ) {
                    if (user != null) {
                        val fileUrls = media.map {
                            val file = context.createTmpFileFromUri(
                                uri = it.uri.toUri(),
                                fileName = it.name.replace(" ", "").take(10)
                            )!!
                            FileModel(
                                file = file,
                                url = Endpoints.FILE_URL(
                                    "${
                                        user.username.lowercase(Locale.ROOT).replace(" ", "")
                                    }/${file.name.replace(" ", "")}.${
                                        it.mediaMimeType.split("/").last()
                                    }"
                                ),
                                mediaMimeType = it.mediaMimeType.split("/").last()
                            )
                        }
                        fileUrls.forEach {
                            viewModel.uploadImage(
                                image = it.file,
                                path = user.username.lowercase(Locale.ROOT).replace(" ", ""),
                                fileName = "${it.file.name}.${it.mediaMimeType}"
                            ).addOnSuccessListener { resp ->
                                Timber.d(resp)
                                it.file.delete()
                            }.addOnFailureListener { exception ->
                                Timber.d(exception)
                                Toaster(context, exception.message.toString(), R.drawable.logo)
                                it.file.delete()
                            }
                        }
                    }
                }

                Text(text = stringResource(id = R.string.edit_),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
//                            navigator.navigate(ChooseMediaDestination(from = "job"))
                        }
                )

            } else {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CakkieButton(
                        text = stringResource(id = R.string.apply_now),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        enabled = job.hasEnoughBalance && job.hasApplied.not(),
                    ) {

                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "share",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}