package com.cakkie.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton2
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.CakkieOrange
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun MyProfile() {
    val img10 = painterResource(id = R.drawable.cake65)
    val img11 = painterResource(id = R.drawable.cake60)
    val img12 = painterResource(id = R.drawable.cake62)
    val love = painterResource(id = R.drawable.gridicons_heart)


    var sizeImage by remember {
        mutableStateOf(IntSize.Zero)
    }
    val gradient = Brush.linearGradient(
        0.0f to Color.Transparent,
        500.0f to Color.Black,
        start = Offset.Zero,
        end = Offset.Infinite,
    )
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { },
            )
            Text(
                text = stringResource(id = R.string.profile),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            Modifier
                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(200.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                GlideImage(
                    model = "https://source.unsplash.com/600x400/?cakes,cover",
                    contentDescription = "cover",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                        .height(150.dp)
                )
                GlideImage(
                    model = "https://source.unsplash.com/100x100/?profile,cute",
                    contentDescription = "profile pic",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(100))
                        .border(
                            width = 3.dp,
                            color = CakkieBackground,
                            shape = RoundedCornerShape(100)
                        )
                )
            }
            Text(
                text = "Jennifer Victor",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Text(
                text = "   Uyo Nwaniba",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CakkieButton2(
                    text = stringResource(id = R.string.edit_profile)
                ) {
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .size(width = 70.dp, height = 34.dp),
                    border = BorderStroke(1.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBackground,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share, contentDescription = "",
                        modifier = Modifier,
                        tint = CakkieBrown
                    )
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .size(width = 70.dp, height = 34.dp),
                    border = BorderStroke(1.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBackground,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings, contentDescription = "",
                        modifier = Modifier,
                        tint = CakkieBrown
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(start = 31.dp, end = 31.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "870",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = stringResource(id = R.string.posts),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Divider(
                    color = CakkieBrown,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "120k",
                        style = MaterialTheme.typography.labelLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = stringResource(id = R.string.following),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Divider(
                    color = CakkieBrown,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "354k",
                        style = MaterialTheme.typography.labelLarge,
                        color = CakkieBrown
                    )
                    Text(
                        text = stringResource(id = R.string.followers),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .size(width = 90.dp, height = 34.dp),
                    border = BorderStroke(1.dp, color = CakkieLightBrown),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieBackground,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(60)
                ) {
                    Text(
                        text = stringResource(id = R.string.posts),
                        style = MaterialTheme.typography.labelSmall,
                        color = CakkieLightBrown
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(width = 200.dp, height = 34.dp)
                        .padding(start = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CakkieOrange,
                        contentColor = CakkieBrown
                    ),
                    shape = RoundedCornerShape(60)
                ) {
                    Text(
                        text = stringResource(id = R.string.favourite_feed),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                items(50) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(width = 118.dp, height = 116.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        GlideImage(
                            model = "https://source.unsplash.com/300x300/?cakes",
                            contentDescription = "",
                            modifier = Modifier
                                .padding(end = 3.dp, bottom = 4.dp)
                                .onGloballyPositioned {
                                    sizeImage = it.size
                                },
                            contentScale = ContentScale.FillBounds
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(gradient)
                        )
                        Row(
                            modifier = Modifier.padding(end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = love, contentDescription = "",
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "100",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.padding(start = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}