package com.cakkie.ui.screens.jobs

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.cakkie.ActivityStates.temp
import com.cakkie.R
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.DateTimePicker
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.JobDetailsDestination
import com.cakkie.ui.screens.destinations.JobsDestination
import com.cakkie.ui.screens.destinations.SetDeliveryAddressDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatNumber
import com.cakkie.utill.toJson
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
//@Destination
@Composable
fun CreateJob(
    item: MutableState<JobModel>,
    viewModel: JobsViewModel = koinViewModel(),
    media: SnapshotStateList<MediaModel> = remember {
        mutableStateListOf()
    },
    fileRecipient: ResultRecipient<ChooseMediaDestination, String>,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val shop = viewModel.shop.observeAsState().value
    val user = viewModel.user.observeAsState().value
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var pageCount by remember {
        mutableIntStateOf(1)
    }
    val pageState = rememberPagerState(pageCount = { pageCount })
    val job = item.value

    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        Timber.d("Selected Date: ${job.deadline}")
        if (job.deadline.isEmpty()) {
            selectedDate.add(Calendar.HOUR_OF_DAY, 12)
        } else {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            selectedDate.time = dateFormat.parse(job.deadline)!!
        }
    }

    val canProceed = job.title.isNotEmpty() && job.description.isNotEmpty()
            && job.salary > 0 && job.meta.size.isNotEmpty()

    var processing by remember {
        mutableStateOf(false)
    }

    fileRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (temp.value != null) {
                    item.value = temp.value as JobModel
                }
                media.addAll(result.value.toObjectList(MediaModel::class.java))
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title) + " *",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                CakkieInputField(
                    value = job.title,
                    onValueChange = {
                        //limit name to 20 characters
                        if (it.length <= 20) {
                            item.value = job.copy(title = it)
                        }
                    },
                    placeholder = stringResource(id = R.string.add_a_title),
                    keyboardType = KeyboardType.Text
                )
                Text(
                    text = "${job.title.length}/20",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.description) + " *",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                CakkieInputField(
                    value = job.description,
                    onValueChange = {
                        //limit description to 500 characters
                        if (it.length <= 500) {
                            item.value = job.copy(description = it)
                        }
                    },
                    placeholder = stringResource(id = R.string.carefully_add_details_to_your_listing_to),
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    modifier = Modifier.height(100.dp)
                )
                Text(
                    text = "${job.description.length}/500",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(20.dp))

                listOf(
                    R.string.product,
                    R.string.size,
                    R.string.quantity,
                    R.string.shape,
                    R.string.flavour,
                    R.string.proposed_price,
                    R.string.expected_completion_date,
                    R.string.location_for_delivery,
                ).forEach { prop ->
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = prop),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        when (prop) {
                            R.string.product -> CakkieFilter(
                                job.productType,
                                options = listOf(
                                    "Cake",
                                    "Pastries",
                                    "Small chops",
                                    "Others"
                                ),
                                screenWidth * 0.5f
                            ) {
                                item.value = job.copy(productType = it)
                            }

                            R.string.size -> CakkieFilter(
                                job.meta.size,
                                options = listOf(
                                    "4 inches H, 20cm W",
                                    "4 inches H, 40cm W",
                                    "6 inches H, 40cm W",
                                    "6 inches H, 60cm W",
                                    "8 inches H, 60cm W",
                                    "8 inches H, 80cm W",
                                    "Specified in description",
                                ),
                                screenWidth * 0.5f
                            ) {
                                item.value = job.copy(meta = job.meta.copy(size = it))
                            }

                            R.string.shape -> CakkieFilter(
                                job.meta.shape,
                                options = listOf(
                                    "Round",
                                    "Oval",
                                    "Squared",
                                    "Star",
                                    "Heart",
                                    "Triangle",
                                    "Specified in description"
                                ),
                                screenWidth * 0.5f
                            ) {
                                item.value = job.copy(meta = job.meta.copy(shape = it))
                            }

                            R.string.flavour -> CakkieFilter(
                                job.meta.flavour,
                                options = listOf(
                                    "Vanilla",
                                    "Chocolate",
                                    "Strawberry",
                                    "Red Velvet",
                                    "Coconut",
                                    "Fruity",
                                    "Specified in description"
                                ),
                                screenWidth * 0.5f
                            ) {
                                item.value = job.copy(meta = job.meta.copy(flavour = it))
                            }

                            R.string.expected_completion_date -> DateTimePicker(
                                label = "Select Date and Time",
                                selectedDate = selectedDate,
                                onDateTimeSelected = { newDate, newHours ->
                                    selectedDate = newDate
//                                    totalHours = newHours
                                }
                            )

                            R.string.location_for_delivery -> Box(
                                Modifier
                                    .padding(start = 20.dp)
                                    .border(
                                        width = 1.dp,
                                        color = CakkieBrown,
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .height(35.dp)
                                    .clip(MaterialTheme.shapes.small),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Row(
                                    Modifier
                                        .padding(vertical = 8.dp, horizontal = 10.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            navigator.navigate(SetDeliveryAddressDestination)
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = user?.address ?: "Set Address",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier,
                                        color = TextColorDark
                                    )

                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = "Dropdown Icon",
                                        tint = CakkieBrown,
                                        modifier = Modifier
//                    .rotate(if (expanded) 180f else 0f)
                                            .size(20.dp)
                                    )
                                }
                            }

                            else -> BasicTextField(
                                value = when (prop) {
                                    R.string.quantity -> item.value.meta.quantity
                                    R.string.proposed_price -> item.value.salary.toString()
                                    else -> ""
                                },
                                onValueChange = {
                                    when (prop) {
                                        R.string.quantity -> item.value =
                                            job.copy(meta = job.meta.copy(quantity = it))

                                        R.string.proposed_price -> item.value =
                                            job.copy(salary = it.ifEmpty { "0" }.toDouble())
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = when (prop) {
                                        R.string.quantity -> KeyboardType.Number
                                        R.string.proposed_price -> KeyboardType.Number
                                        else -> KeyboardType.Text
                                    }
                                ),
                                modifier = Modifier
                                    .width(screenWidth * 0.5f)
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
                                    if (prop == R.string.proposed_price) {
                                        Text(
                                            text = "NGN",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextColorInactive,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                    if (prop == R.string.quantity) {
                                        Box(
                                            modifier = Modifier
                                                .clip(MaterialTheme.shapes.small)
                                                .clickable {
                                                    val quantity = job.meta.quantity.ifEmpty { "0" }
                                                    if (quantity.toInt() > 1) {
                                                        item.value = job.copy(
                                                            meta = job.meta.copy(
                                                                quantity = (quantity.toInt() - 1).toString()
                                                            )
                                                        )
                                                    }
                                                }
                                                .background(CakkieBrown)
                                                .size(35.dp), contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "-",
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = CakkieBackground,
                                            )
                                        }
                                    }
                                    Text(
                                        text = when (prop) {
                                            R.string.quantity -> job.meta.quantity.ifEmpty { "0" }
                                            R.string.proposed_price -> formatNumber(job.salary)
                                            else -> ""
                                        },
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp),
                                        color = if (when (prop) {
                                                R.string.quantity -> job.meta.quantity.isEmpty()
                                                R.string.proposed_price -> job.salary == 0.0
                                                else -> false
                                            }
                                        ) TextColorInactive else TextColorDark,
                                    )
                                    if (prop == R.string.quantity) {
                                        Box(
                                            modifier = Modifier
                                                .clip(MaterialTheme.shapes.small)
                                                .clickable {
                                                    item.value = job.copy(
                                                        meta = job.meta.copy(
                                                            quantity = (job.meta.quantity
                                                                .ifEmpty { "0" }
                                                                .toInt() + 1).toString()
                                                        )
                                                    )
                                                }
                                                .background(CakkieBrown)
                                                .size(35.dp), contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "+",
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = CakkieBackground,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.add_photos),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    state = listState,
                    flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) {
                    items(
                        items = media + MediaModel(
                            uri = "android.resource://${context.packageName}/${R.drawable.add_media}",
                            name = "Add Media",
                            isVideo = false,
                            mediaMimeType = "image",
                            dateAdded = 0L
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(169.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .padding(horizontal = 4.dp)
                                .background(Color.White.copy(alpha = 0.6f))
                                .width(screenWidth * if (media.size > 1) 0.78f else 0.87f)
                        ) {
                            GlideImage(
                                model = it.uri.toUri(),
                                contentDescription = "Media",
                                modifier = Modifier
                                    .height(169.dp)
                                    .blur(radius = 10.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                            )
                            if (it.isVideo) {
                                val exoPlayer = remember {
                                    ExoPlayer.Builder(context)
                                        .build()
                                        .apply {
                                            val defaultDataSourceFactory =
                                                DefaultDataSource.Factory(context)
                                            val dataSourceFactory: DataSource.Factory =
                                                DefaultDataSource.Factory(
                                                    context,
                                                    defaultDataSourceFactory
                                                )
                                            val source =
                                                ProgressiveMediaSource.Factory(dataSourceFactory)
                                                    .createMediaSource(MediaItem.fromUri(it.uri))
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
                                    model = it.uri.toUri(),
                                    contentDescription = "Media",
                                    modifier = Modifier
                                        .height(169.dp)
                                        .clickable {
                                            temp.value = job
                                            navigator.navigate(ChooseMediaDestination(from = "job")) {
                                                popUpTo(JobsDestination) {
                                                    saveState = true
                                                }
                                                restoreState = true
                                                launchSingleTop = true
                                            }
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

                Spacer(modifier = Modifier.height(30.dp))
                CakkieButton(
                    text = stringResource(id = R.string.review),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    enabled = canProceed,
                    processing = processing
                ) {
                    processing = true
                    if (shop != null) {
                        val dateFormat = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                            Locale.getDefault()
                        )
                        val currentDate = dateFormat.format(Date())
                        val deadlineDate = dateFormat.format(selectedDate.time)
                        if (user != null) {

                            Timber.d("Selected Date: ${deadlineDate}")
                            item.value = job.copy(
                                createdAt = currentDate,
                                deadline = deadlineDate,
                                currencySymbol = "NGN",
                                updatedAt = currentDate,
                                media = media.map { it.uri },
                            )
                            navigator.navigate(
                                JobDetailsDestination(
                                    "create",
                                    job.copy(
                                        createdAt = currentDate,
                                        deadline = deadlineDate,
                                        currencySymbol = "NGN",
                                        updatedAt = currentDate,
                                        media = media.map { it.uri },
                                    ),
                                    media.toList().toJson()
                                )
                            ) {
                                launchSingleTop = true
                            }
                        }
                    } else {
                        Toaster(
                            context,
                            "Something went wrong, please restart app",
                            R.drawable.logo
                        ).show()
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }

    }
}