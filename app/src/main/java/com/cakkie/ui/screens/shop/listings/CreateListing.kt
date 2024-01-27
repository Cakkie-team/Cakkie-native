package com.cakkie.ui.screens.shop.listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun CreateListing(navigator: DestinationsNavigator) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val files = remember {
        mutableListOf<String>()
    }
    Column(Modifier.fillMaxSize()) {
//        Spacer(modifier = Modifier.height(20.dp))
        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back", contentScale = ContentScale.FillWidth,
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
                    .align(Alignment.Center)
            )
        }
        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(100) { item ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        GlideImage(
                            model = "https://source.unsplash.com/400x400/?cakes?$item",
                            contentDescription = "cake",
                            modifier = Modifier
                                .clickable {
                                    if (!files.contains(item.toString())) {
                                        files.add(item.toString())
                                    } else {
                                        files.remove(item.toString())
                                    }
                                    Timber.d(
                                        files
                                            .contains(item.toString())
                                            .toString()
                                    )
                                }
                                .fillMaxWidth()
                                .height(screenWidth / 3),
                            contentScale = ContentScale.FillBounds
                        )
                        Checkbox(
                            checked = files.contains(item.toString()),
                            onCheckedChange = {
                                if (!files.contains(item.toString())) {
                                    files.add(item.toString())
                                } else {
                                    files.remove(item.toString())
                                }
                            },
                            modifier = Modifier
                                .size(20.dp)
                                .align(
                                    Alignment.TopEnd
                                ),
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = CakkieBackground,
                                checkedColor = CakkieBrown,
                                uncheckedColor = CakkieBrown.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
            CakkieButton(
                text = stringResource(id = R.string.done),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.BottomCenter)
            ) {

            }
        }
    }
}