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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.TextFieldValue
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
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieFilter
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.DateTimePicker
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.util.Calendar
import java.util.Locale

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun CreateJob(
    media: SnapshotStateList<MediaModel> = remember {
        mutableStateListOf()
    },
    fileRecipient: ResultRecipient<ChooseMediaDestination, String>,
    navigator: DestinationsNavigator
) {
    val viewModel: ShopViewModel = koinViewModel()
    val context = LocalContext.current
    val shop = viewModel.shop.observeAsState().value
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var pageCount by remember {
        mutableIntStateOf(1)
    }
    val pageState = rememberPagerState(pageCount = { pageCount })

    var name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var description by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val prices = remember {
        mutableStateListOf(TextFieldValue(""))
    }
    var sizes = remember {
        TextFieldValue("4 inches H, 20cm W")
    }
    var quantity by remember {
        mutableStateOf(TextFieldValue("1"))
    }
    var shape by remember {
        mutableStateOf(TextFieldValue("Round"))
    }

    var flavour by remember {
        mutableStateOf(TextFieldValue("Vanilla"))
    }
    var product by remember {
        mutableStateOf(TextFieldValue("Cake"))
    }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        selectedDate.add(Calendar.HOUR_OF_DAY, 12)
    }

    val coroutineScope = rememberCoroutineScope()
    val canProceed = name.text.isNotEmpty() && description.text.isNotEmpty()
            && prices.all { it.text.isNotEmpty() } && sizes.text.isNotEmpty()

    var processing by remember {
        mutableStateOf(false)
    }

    fileRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
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
                    text = stringResource(id = R.string.listing_name),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                CakkieInputField(
                    value = name,
                    onValueChange = {
                        //limit name to 20 characters
                        if (it.text.length <= 20) {
                            name = it
                        }
                    },
                    placeholder = stringResource(id = R.string.add_a_title),
                    keyboardType = KeyboardType.Text
                )
                Text(
                    text = "${name.text.length}/20",
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
                    value = description,
                    onValueChange = {
                        //limit description to 500 characters
                        if (it.text.length <= 500) {
                            description = it
                        }
                    },
                    placeholder = stringResource(id = R.string.carefully_add_details_to_your_listing_to),
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    modifier = Modifier.height(100.dp)
                )
                Text(
                    text = "${description.text.length}/500",
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
                                product.text,
                                options = listOf(
                                    "Cake",
                                    "Pastries",
                                    "Small chops",
                                    "Others"
                                ),
                                screenWidth * 0.5f
                            ) {
                                product = TextFieldValue(it)
                            }

                            R.string.size -> CakkieFilter(
                                sizes.text,
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
                                sizes = TextFieldValue(it)
                            }

                            R.string.shape -> CakkieFilter(
                                shape.text,
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
                                shape = TextFieldValue(it)
                            }

                            R.string.flavour -> CakkieFilter(
                                flavour.text,
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
                                flavour = TextFieldValue(it)
                            }

                            R.string.expected_completion_date -> DateTimePicker(
                                label = "Select Date and Time",
                                selectedDate = selectedDate,
                                onDateTimeSelected = { newDate, newHours ->
                                    selectedDate = newDate
//                                    totalHours = newHours
                                }
                            )

                            else -> BasicTextField(
                                value = when (prop) {
                                    R.string.quantity -> quantity
                                    else -> TextFieldValue("")
                                },
                                onValueChange = {
                                    when (prop) {
                                        R.string.quantity -> quantity = it
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                                    Text(
                                        text = when (prop) {
                                            R.string.quantity -> quantity.text.ifEmpty { "0 pieces" }
                                            else -> ""
                                        },
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(8.dp),
                                        color = if (when (prop) {
                                                R.string.quantity -> quantity.text.isEmpty()
                                                else -> false
                                            }
                                        ) TextColorInactive else TextColorDark,
                                    )
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
                                .padding(horizontal = 8.dp)
                                .background(Color.White.copy(alpha = 0.6f))
                                .width(screenWidth * if (media.size > 1) 0.8f else 0.9f)
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

                Spacer(modifier = Modifier.height(30.dp))
                CakkieButton(
                    text = stringResource(id = R.string.done),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canProceed,
                    processing = processing
                ) {
                    processing = true
                    if (shop != null) {
                        val fileUrls = media.map {
                            val file = context.createTmpFileFromUri(
                                uri = it.uri.toUri(),
                                fileName = it.name.replace(" ", "").take(10)
                            )!!
                            FileModel(
                                file = file,
                                url = Endpoints.FILE_URL(
                                    "${
                                        shop.name.lowercase(Locale.ROOT).replace(" ", "")
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
                                path = shop.name.lowercase(Locale.ROOT).replace(" ", ""),
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
//                        viewModel.createListing(
//                            name = name.text,
//                            description = description.text,
//                            prices = prices.map { it.text.toInt() },
//                            sizes = sizes.text,
//                            media = fileUrls.map { it.url },
//                            shopId = shop.id,
//                            availability = availability.text,
//                            meta = listOf(
//                                Pair("quantity", quantity.text),
//                                Pair("shape", shape.text),
//                                Pair("flavour", flavour.text)
//                            )
//                        ).addOnSuccessListener {
//                            processing = false
////                            navigator.navigate(ShopDestination) {
////                                popUpTo(ChooseMediaDestination) {
////                                    inclusive = true
////                                }
////                                popUpTo(CreateListingDestination) {
////                                    inclusive = true
////                                }
////                                launchSingleTop = true
////                            }
//                        }.addOnFailureListener { exception ->
//                            processing = false
//                            Toaster(context, exception, R.drawable.logo)
//                            Timber.d(exception)
//                        }
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