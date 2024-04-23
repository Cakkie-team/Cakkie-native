package com.cakkie.ui.screens.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.screens.destinations.ChooseMediaDestination
import com.cakkie.ui.screens.shop.MediaModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieOrange
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.toObjectList
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun Chat(
    id: String,
    fileRecipient: ResultRecipient<ChooseMediaDestination, String>,
    navigator: DestinationsNavigator
) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp.dp
    var files by remember {
        mutableStateOf(emptyList<MediaModel>())
    }
    fileRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                files = result.value.toObjectList(MediaModel::class.java)
            }
        }
    }
    var replyTo by remember {
        mutableStateOf("")
    }
    var showOption by remember {
        mutableStateOf(false)
    }
    val chats = (0..15).toList()
    var message by remember { mutableStateOf(TextFieldValue("")) }

    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(24.dp)
                    )
                }
                var isLoading by remember {
                    mutableStateOf(false)
                }
                AsyncImage(
                    model = "https://source.unsplash.com/80x80/?profile",
                    contentDescription = "profile pic",
                    onState = {
                        //update isLoaded
                        isLoading = it is AsyncImagePainter.State.Loading
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {

                        }
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = CakkieBrown.copy(0.8f)
                        )
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Cakkie Support",
                        color = TextColorDark,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Online",
                        color = CakkieGreen,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp
                    )
                }
            }

            IconButton(onClick = { showOption = true }) {
                Image(
                    painter = painterResource(id = R.drawable.options),
                    contentDescription = "Back",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CakkieOrange)
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Award Contract",
                color = TextColorDark,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        LazyColumn(Modifier.weight(1f), reverseLayout = true) {
            items(items = chats, key = { index -> index }) {
                ChatItem(it) {
                    replyTo = "Chat item $it"
                }
            }
        }
        AnimatedVisibility(files.isNotEmpty()) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = CardDefaults.elevatedShape,
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBackground,
                ),
            ) {
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    files.forEach {
                        Card(
                            Modifier
                                .padding(vertical = 5.dp, horizontal = 3.dp)
                                .size(80.dp, 60.dp),
                            shape = CardDefaults.elevatedShape,
                            colors = CardDefaults.cardColors(
                                containerColor = CakkieBackground,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                GlideImage(
                                    model = it.uri,
                                    contentDescription = it.name,
                                    modifier = Modifier
                                        .size(80.dp, 60.dp),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = {
                                        files = files.filter { file -> file.uri != it.uri }
                                    }, modifier = Modifier
                                        .align(Alignment.Center)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.cancel),
                                        contentDescription = "cancel",
                                        tint = CakkieBackground,
                                        modifier = Modifier
                                            .background(
                                                CakkieBrown.copy(0.5f), shape = CircleShape
                                            )
                                            .size(24.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
        AnimatedVisibility(replyTo.isNotEmpty()) {
//            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                Modifier
                    .heightIn(max = 80.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    shape = CardDefaults.elevatedShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(0.5f),
                    ),
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "james bond",
                                color = CakkieOrange,
                                style = MaterialTheme.typography.bodyLarge,
//                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = replyTo,
                                maxLines = 2,
                                color = CakkieBackground,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .fillMaxHeight()
                        ) {
                            GlideImage(
                                model = "https://picsum.photos/200/300",
                                contentDescription = "chat image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(CakkieBrown.copy(alpha = 0.5f)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                IconButton(
                    onClick = {
                        replyTo = ""
                    }, modifier = Modifier
                        .offset(x = (-6).dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "cancel",
                        tint = CakkieBackground,
                        modifier = Modifier
                            .background(
                                CakkieBrown.copy(0.5f), shape = CircleShape
                            )
                            .size(24.dp)
                    )
                }
            }
//            Spacer(modifier = Modifier.padding(8.dp))
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(
                containerColor = CakkieBackground
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = {
                        Text(
                            text = "Type a message",
                            color = TextColorInactive,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    leadingIcon = {
                        IconButton(onClick = {
                            navigator.navigate(ChooseMediaDestination(from = "chat"))
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.fluent_attach),
                                contentDescription = "Back",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.width(24.dp)
                            )
                        }
                    },
                    trailingIcon = {
                        AnimatedVisibility(visible = message.text.isNotEmpty()) {
                            IconButton(onClick = { }) {
                                Image(
                                    painter = painterResource(id = R.drawable.send_fill),
                                    contentDescription = "Back",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.width(24.dp)
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CakkieBrown,
                        textColor = TextColorDark
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    AnimatedVisibility(visible = showOption) {
        Popup(alignment = Alignment.TopEnd, onDismissRequest = { showOption = false }) {
            Card(
                Modifier.padding(16.dp),
                shape = CardDefaults.elevatedShape,
                colors = CardDefaults.cardColors(
                    containerColor = CakkieBackground
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.report),
                            contentDescription = stringResource(
                                id = R.string.report
                            ),
                            tint = CakkieBrown,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.report_user),
                            color = TextColorDark,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}