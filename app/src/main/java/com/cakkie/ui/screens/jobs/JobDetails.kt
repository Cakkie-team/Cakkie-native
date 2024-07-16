package com.cakkie.ui.screens.jobs

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.cakkie.networkModels.CurrencyRate
import com.cakkie.networkModels.FileModel
import com.cakkie.networkModels.JobEdit
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.DateTimePicker
import com.cakkie.ui.screens.destinations.ConfirmPinDestination
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.Error
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber
import com.cakkie.utill.isVideoUrl
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun JobDetails(
    id: String,
    item: JobModel = JobModel(),
    files: String = "",
    onEdit: ResultBackNavigator<JobEdit>,
    confirmPinResult: ResultRecipient<ConfirmPinDestination, CurrencyRate>,
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
    var processing by rememberSaveable { mutableStateOf(false) }
    var applying by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf("") }
    var proposedPrice by rememberSaveable { mutableStateOf("") }
    var proposedDeadline by rememberSaveable { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        selectedDate.time = dateFormat.parse(job.deadline)!!
        proposedDeadline = job.deadline
    }

//    var isMuted by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(key1 = id) {
        if (id != "create") {
            viewModel.getJob(id).addOnSuccessListener {
                job = it
            }.addOnFailureListener {
                Toaster(
                    context, it.localizedMessage ?: "Something went wrong", R.drawable.logo
                )
            }
        } else {
            job = item
        }
    }

    //handle back press to trigger onEdit if id is create
    BackHandler {
        if (id == "create") {
            onEdit.navigateBack(JobEdit(job, media))
        } else {
            navigator.popBackStack()
        }
    }


    confirmPinResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                processing = true
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

                    viewModel.createJob(
                        salary = result.value.amount.toDouble(),
                        productType = job.productType,
                        deadline = job.deadline,
                        proposalFee = job.proposalFee,
                        address = job.address,
                        city = job.city,
                        state = job.state,
                        country = job.country,
                        latitude = job.latitude,
                        longitude = job.longitude,
                        currencySymbol = result.value.symbol,
                        title = job.title,
                        media = fileUrls.map { it.url },
                        description = job.description,
                        pin = result.value.pin,
                        meta = job.meta.toListOfPairs().map { it.first.lowercase() to it.second }
                    ).addOnSuccessListener {
                        processing = false
                        navigator.navigate(JobDetailsDestination(it.id, it)) {
                            launchSingleTop = true
                        }
                    }.addOnFailureListener { exception ->
                        processing = false
                        Toaster(context, exception, R.drawable.logo)
                        Timber.d(exception)
                    }
                }
            }
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

            if (id != "create") {
                Text(
                    text = "Applications: ${job.totalProposal} - ${job.totalProposal.plus(2)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
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
                            .padding(horizontal = 5.dp)
                            .background(Color.White.copy(alpha = 0.6f))
                            .width(screenWidth * if (job.media.size > 1) 0.80f else 0.90f)
                    ) {
                        GlideImage(
                            model = media.uri.toUri(),
                            contentDescription = "Media",
                            modifier = Modifier
                                .height(179.dp)
                                .blur(radius = if (media.isVideo) 10.dp else 0.dp)
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

            if (id != "create") {
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
                                text = stringResource(
                                    id = if (job.userId == user?.id) {
                                        when (job.status) {
                                            "COMPLETED" -> R.string.completed
                                            "PENDING" -> R.string.you_have_not_hired
                                            else -> R.string.work_in_progress
                                        }
                                    } else {
                                        if (job.hasApplied) R.string.applied
                                        else R.string.you_have_not_appl
                                    }
                                ),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.widthIn(max = 150.dp),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(
                                painter = painterResource(
                                    id = if (job.userId == user?.id) {
                                        when (job.status) {
                                            "COMPLETED" -> R.drawable.approved
                                            "PENDING" -> R.drawable.not
                                            else -> R.drawable.done
                                        }
                                    } else {
                                        if (job.hasApplied) R.drawable.approved
                                        else R.drawable.not
                                    }
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
            }


            if (id == "create") {
                Spacer(modifier = Modifier.height(20.dp))
                CakkieButton(
                    text = stringResource(id = R.string.post),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f),
                    processing = processing
                ) {
                    navigator.navigate(
                        ConfirmPinDestination(
                            CurrencyRate(
                                symbol = job.currencySymbol,
                                amount = job.salary.toString()
                            )
                        )
                    )

                }

                Text(text = stringResource(id = R.string.edit_),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            onEdit.navigateBack(JobEdit(job, media))
//                            navigator.navigate(ChooseMediaDestination(from = "job"))
                        }
                )

            } else if (job.userId == user?.id) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .clickable {
//                            navigator.navigate(ChooseMediaDestination(from = "job"))
                            }
                            .border(
                                1.dp,
                                CakkieBrown,
                                RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp)
                            )
                            .height(96.dp)
                            .background(Color.White, RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp))
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.view_appl),
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(10.dp, 10.dp, 0.dp, 5.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.check_out_all_appl),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextColorInactive,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 2.dp, 10.dp, 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = stringResource(id = R.string.applicants, job.totalProposal),
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextColorDark,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                                    .rotate(180f),
                                tint = CakkieBrown
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        Modifier
                            .background(
                                if (job.status == "PENDING") CakkieLightBrown else CakkieGreen,
                                RoundedCornerShape(5.dp, 5.dp, 0.dp, 0.dp)
                            )
                            .weight(0.45f)
                            .height(96.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.awarded),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                }
            } else if (applying.not()) {
                if (job.hasEnoughBalance.not()) {
                    Column(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(250.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(
                                id = R.string.insuficint_balance,
                                job.proposalFee
                            ),
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
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CakkieButton(
                        text = stringResource(id = R.string.apply_now),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        enabled = !job.hasEnoughBalance && job.hasApplied.not(),
                    ) {
                        if (user?.hasShop == true) {
                            applying = true
                        } else {
                            //navigate to create shop screen
                            navigator.navigate(ShopDestination) {
                                launchSingleTop = true
                            }

                        }
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

            if (applying) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(Modifier.fillMaxWidth(), thickness = 4.dp, color = CakkieLightBrown)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.write_a_proposal),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CakkieInputField(
                        value = message,
                        onValueChange = {
                            //limit description to 500 characters
                            if (it.length <= 500) {
                                message = it
                            }
                        },
                        placeholder = stringResource(id = R.string.write_a_few_words_here),
                        keyboardType = KeyboardType.Text,
                        singleLine = false,
                        modifier = Modifier.height(100.dp)
                    )
                    Text(
                        text = "${message.length}/500",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.propose_a_price),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.proposed_price_by_buyer),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .border(
                                        width = 1.dp,
                                        color = CakkieBrown,
                                        shape = MaterialTheme.shapes.small
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = job.currencySymbol,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextColorInactive,
                                    modifier = Modifier.padding(8.dp)
                                )

                                Text(
                                    text = formatNumber(job.salary),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp),
                                    color = TextColorInactive,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(0.2f))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.proposed_price_by_you),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            BasicTextField(
                                value = proposedPrice,
                                onValueChange = {
                                    proposedPrice = it
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = CakkieBrown,
                                            shape = MaterialTheme.shapes.small
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = job.currencySymbol,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextColorInactive,
                                        modifier = Modifier.padding(8.dp)
                                    )

                                    Text(
                                        text = formatNumber(proposedPrice.ifEmpty { "0" }
                                            .toDouble()),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp),
                                        color = TextColorDark,
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.proposed_completion),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.proposed_date),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .border(
                                        width = 1.dp,
                                        color = CakkieBrown,
                                        shape = MaterialTheme.shapes.small
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = job.deadline.formatDateTime(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp),
                                    color = TextColorInactive,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(0.2f))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.proposed_a_date),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 14.sp,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            DateTimePicker(
                                label = "Select Date and Time",
                                selectedDate = selectedDate,
                                paddingStart = 0.dp,
                                onDateTimeSelected = { newDate, newHours ->
                                    selectedDate = newDate
                                    val dateFormat = SimpleDateFormat(
                                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                        Locale.getDefault()
                                    )
                                    proposedDeadline = dateFormat.format(selectedDate.time)
//                                    totalHours = newHours
                                }
                            )
                        }
                    }

                }

                Column(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(
                            id = R.string.please_ensure_you,
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieGreen,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    CakkieButton(
                        text = stringResource(id = R.string.submit),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        enabled = message.isNotEmpty() && proposedPrice.isNotEmpty(),
                        processing = processing
                    ) {
                        processing = true
                        viewModel.submitProposal(
                            deadline = proposedDeadline,
                            proposalAmount = proposedPrice.toDouble(),
                            message = message,
                            jobId = job.id
                        ).addOnSuccessListener {
                            applying = false
                            processing = false
                            Toaster(context, "Proposal submitted successfully", R.drawable.logo)
                        }.addOnFailureListener {
                            applying = false
                            processing = false
                            Toaster(context, it, R.drawable.logo)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}