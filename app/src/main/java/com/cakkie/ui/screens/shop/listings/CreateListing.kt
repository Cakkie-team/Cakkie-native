package com.cakkie.ui.screens.shop.listings

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.toObjectList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun CreateListing(files: String, navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val context = LocalContext.current
    //convert string to list of media
    val media = files.toObjectList(MediaModel::class.java)
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var description by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var price by remember {
        mutableStateOf(TextFieldValue(""))
    }


    Column(
        Modifier
            .fillMaxSize()
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
                stringResource(id = R.string.shop),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_a_new_listing),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.please_fill_out_the_form_below_to_post_a_new_listing),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 11.sp,
                color = TextColorInactive,
            )
        }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.media),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {
                items(
                    items = media
                ) {
                    Box(
                        modifier = Modifier
                            .height(169.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .padding(horizontal = 8.dp)
                            .background(Color.Black.copy(alpha = 0.6f))
                            .width(screenWidth * 0.8f)
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
                                    .clip(MaterialTheme.shapes.medium)
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }

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

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.price) + " *",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    BasicTextField(
                        value = price,
                        onValueChange = {
                            price = it
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(screenWidth * 0.5f)
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
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextColorInactive,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = price.text,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                listOf(
                    R.string.size,
                    R.string.quantity,
                    R.string.shape,
                    R.string.flavour,
                ).forEach { prop ->
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = prop) + when (prop) {
                                R.string.size -> " *"
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        BasicTextField(
                            value = price,
                            onValueChange = {
                                price = it
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(screenWidth * 0.5f)
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
                                    text = price.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(8.dp)
                                )

                                if (prop == R.string.size) {
                                    Text(
                                        text = "Inches",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextColorInactive,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                CakkieButton(
                    text = stringResource(id = R.string.done),
                    modifier = Modifier.fillMaxWidth()
                ) {

                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

    }
}