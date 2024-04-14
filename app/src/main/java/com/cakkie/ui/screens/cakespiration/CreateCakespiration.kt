package com.cakkie.ui.screens.cakespiration

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.VideoPlayer
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.destinations.CreateCakespirationDestination
import com.cakkie.ui.screens.destinations.EditVideoDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.screens.shop.listings.FileModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.toObject
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.text.NumberFormat
import java.util.Locale

@OptIn(UnstableApi::class)
@Destination
@Composable
fun CreateCakespiration(file: String, navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val shop = viewModel.shop.observeAsState().value
    var isMuted by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val media = file.toObject(MediaModel::class.java)
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
                        .createMediaSource(MediaItem.fromUri(media.uri))
                setMediaSource(source)
                prepare()
            }
    }

    var prices by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    val canProceed = description.text.isNotEmpty()
            && prices.text.isNotEmpty()

    var processing by remember {
        mutableStateOf(false)
    }
    var availability by remember {
        mutableStateOf(TextFieldValue("Please note that under normal circumstances, this cake requires a minimum of 24 hours to prepare and will be delivered to you within 24 hours after preparation."))
    }
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            IconButton(modifier = Modifier
                .align(Alignment.CenterStart), onClick = { navigator.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = "New Cakespiration",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                color = CakkieBrown,
                fontSize = 18.sp
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black)
                    .fillMaxSize(0.6f),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(
                    exoPlayer = exoPlayer,
                    isPlaying = true,
                    mute = isMuted,
                    onMute = {
                        isMuted = it
                    },
                    vResizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            isMuted = !isMuted
                        }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.9f)
            ) {
                CakkieInputField(
                    value = description,
                    onValueChange = {
                        //limit description to 500 characters
                        if (it.text.length <= 500) {
                            description = it
                        }
                    },
                    placeholder = stringResource(id = R.string.write_something_to_describe_your_cakespiration),
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                )
                Text(
                    text = "${description.text.length}/500",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End),
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.base_price) + " *",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )

                BasicTextField(
                    value = prices,
                    onValueChange = {
                        prices = it
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.8f)
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
                    ) {
                        Text(
                            text = "NGN",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColorInactive,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = NumberFormat.getInstance().format(
                                prices.text.ifEmpty { "0" }.toInt()
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (prices.text.isEmpty())
                                TextColorInactive else TextColorDark,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CakkieButton(
                text = stringResource(id = R.string.post),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                enabled = canProceed,
                processing = processing
            ) {
                processing = true
                if (shop != null) {
                    val thisFile = context.createTmpFileFromUri(
                        uri = media.uri.toUri(),
                        fileName = media.name.replace(" ", "")
                    )!!

                    val fileUrls = FileModel(
                        file = thisFile,
                        url = Endpoints.FILE_URL(
                            "${
                                shop.name.lowercase(Locale.ROOT).replace(" ", "")
                            }/${thisFile.name.replace(" ", "")}.${
                                media.mediaMimeType.split("/").last()
                            }"
                        ),
                        mediaMimeType = media.mediaMimeType.split("/").last()
                    )
                    viewModel.uploadImage(
                        image = fileUrls.file,
                        path = shop.name.lowercase(Locale.ROOT).replace(" ", ""),
                        fileName = "${fileUrls.file.name}.${fileUrls.mediaMimeType}"
                    ).addOnSuccessListener { resp ->
                        Timber.d(resp)
                        fileUrls.file.delete()
                    }.addOnFailureListener { exception ->
                        Timber.d(exception)
                        Toaster(context, exception.message.toString(), R.drawable.logo).show()
                        fileUrls.file.delete()
                    }
                    viewModel.createListing(
                        name = description.text.split(" ").take(3).joinToString(" "),
                        description = description.text,
                        prices = listOf(prices.text.toInt()),
                        sizes = listOf(),
                        media = listOf(fileUrls.url),
                        shopId = shop.id,
                        availability = availability.text,
                        meta = listOf(),
                        isListing = false
                    ).addOnSuccessListener {
                        processing = false
                        navigator.navigate(ShopDestination) {
                            popUpTo(ChooseMediaDestination) {
                                inclusive = true
                            }
                            popUpTo(EditVideoDestination) {
                                inclusive = true
                            }
                            popUpTo(CreateCakespirationDestination) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }.addOnFailureListener { exception ->
                        processing = false
                        Toaster(context, exception, R.drawable.logo)
                        Timber.d(exception)
                    }
                } else {
                    Toaster(
                        context,
                        "Something went wrong, please restart app",
                        R.drawable.logo
                    ).show()
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}