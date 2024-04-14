package com.cakkie.ui.screens.shop.listings

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.CreateListingDestination
import com.cakkie.ui.screens.destinations.EditVideoDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.toJson
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
@Destination
@Composable
fun ChooseMedia(default: Int = R.string.all, navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val context = LocalContext.current
    val medias = remember {
        mutableStateListOf<MediaModel>()
    }
    var filter by remember {
        mutableIntStateOf(default)
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val files = remember {
        mutableStateListOf<MediaModel>()
    }
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            READ_MEDIA_IMAGES, READ_MEDIA_VIDEO
        )
    )

    LaunchedEffect(key1 = permissionState) {
        if (permissionState.allPermissionsGranted) {
            medias.addAll(viewModel.getMedias(context))
        } else {
            permissionState.launchMultiplePermissionRequest()
        }
    }

//    Timber.d(medias.toString())
    Column(Modifier.fillMaxSize()) {
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
            Row(
                Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(bottomStart = 15.dp, topEnd = 15.dp))
                    .background(
                        CakkieBrown.copy(alpha = 0.5f)
                    ),
            ) {
                listOf(
                    R.string.all, R.string.images, R.string.videos
                ).forEach {
                    Box(
                        Modifier
                            .clickable { filter = it }
                            .background(
                                if (it == filter) {
                                    CakkieBrown
                                } else {
                                    CakkieBrown.copy(alpha = 0.5f)
                                }
                            )
                            .height(35.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = it),
                            modifier = Modifier.padding(8.dp),
                            color = CakkieBackground
                        )
                    }
                }
            }

            Text(
                stringResource(id = R.string.selected, files.size),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Box(
            Modifier.fillMaxSize()
        ) {

            if (permissionState.allPermissionsGranted) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    columns = GridCells.Fixed(3)
                ) {
                    items(
                        items = medias.filter {
                            when (filter) {
                                R.string.all -> true
                                R.string.images -> !it.isVideo
                                R.string.videos -> it.isVideo
                                else -> true
                            }
                        },
                    ) { item ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                        ) {
                            GlideImage(
                                model = item.uri.toUri(),
                                contentDescription = "cake",
                                modifier = Modifier
                                    .clickable {
                                        if (!files.contains(item) && files.size < 5) {
                                            if (item.isVideo) {
                                                files.clear()
                                            } else {
                                                //check if content of files has video
                                                files
                                                    .toList()
                                                    .forEach {
                                                        if (it.isVideo) {
                                                            files.remove(it)
                                                        }
                                                    }
                                            }
                                            files.add(item)
                                        } else {
                                            files.remove(item)
                                        }
                                        Timber.d(
                                            files
                                                .contains(item)
                                                .toString()
                                        )
                                    }
                                    .fillMaxWidth()
                                    .height(screenWidth / 3),
                                contentScale = ContentScale.Crop)
                            if (item.isVideo) {
                                Icon(
                                    painter = painterResource(id = R.drawable.video),
                                    contentDescription = stringResource(
                                        id = R.string.video
                                    ),
                                    tint = CakkieBackground,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(4.dp)
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .align(Alignment.TopStart)
                                )
                            }
                            Checkbox(
                                checked = files.contains(item), onCheckedChange = {
                                    if (!files.contains(item) && files.size < 5) {
                                        files.add(item)
                                    } else {
                                        files.remove(item)
                                    }
                                }, modifier = Modifier
                                    .size(20.dp)
                                    .align(
                                        Alignment.TopEnd
                                    ), colors = CheckboxDefaults.colors(
                                    checkmarkColor = CakkieBackground,
                                    checkedColor = CakkieBrown,
                                    uncheckedColor = CakkieBrown.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }
            } else {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.media_permission_required),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        color = CakkieBrown,
                    )
                    CakkieButton(
                        text = stringResource(id = R.string.grant_permission),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                }
            }


            CakkieButton(
                text = stringResource(id = R.string.done),
                enabled = files.size > 0,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.BottomCenter)
            ) {
                //check if files has video and decide to navigate to create listing
                if (files.toList().all { !it.isVideo }) {
                    //convert to json and send to next screen
                    navigator.navigate(
                        CreateListingDestination(files = files.toList().toJson())
                    ) {
                        launchSingleTop = true
                    }
                } else {
                    navigator.navigate(
                        EditVideoDestination(file = files.toList().first().toJson())
                    ) {
                        launchSingleTop = true
                    }
                }

            }
        }
    }
}