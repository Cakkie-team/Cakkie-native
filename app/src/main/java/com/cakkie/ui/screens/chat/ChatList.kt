package com.cakkie.ui.screens.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.R
import com.cakkie.networkModels.Conversation
import com.cakkie.networkModels.Message
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.formatDate
import com.cakkie.utill.toObject
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Destination
@Composable
fun ChatList(navigator: DestinationsNavigator) {
    val viewModel: ChatViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    val conversations = viewModel.conversations.observeAsState().value
    val convos = remember {
        mutableStateListOf<Conversation>()
    }
    var query by remember {
        mutableStateOf(TextFieldValue(""))
    }

    LaunchedEffect(key1 = query.text) {
        viewModel.getConversations(query.text)
    }

    LaunchedEffect(key1 = conversations) {
        convos.addAll(conversations?.data?.filterNot { res ->
            convos.any { it.id == res.id }
        } ?: emptyList())
    }

    if (user != null) {
        DisposableEffect(Unit) {
            viewModel.socket.on("newMessage-${user.id}") {
                Timber.d("delivered: $it")
                val message = it[0].toString().toObject(Message::class.java)
                viewModel.deliveredChat(user.id, message.id)
            }

            onDispose {
                viewModel.socket.off("newMessage-${user.id}")
            }
        }
    }

    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(
                        id = R.string.cakkie_logo
                    ),
                    modifier = Modifier
                        .size(27.dp),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.chat),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = CakkieBrown,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = {
                navigator.navigate(ChatDestination("support"))
            }) {
                Image(
                    painter = painterResource(id = R.drawable.support),
                    contentDescription = stringResource(
                        id = R.string.support
                    ),
                    modifier = Modifier
                        .size(27.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CakkieInputField(
                value = query,
                onValueChange = { query = it },
                placeholder = stringResource(id = R.string.search),
                keyboardType = KeyboardType.Text,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search",
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .height(55.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (convos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat),
                            contentDescription = stringResource(id = R.string.chat),
                            modifier = Modifier.size(150.dp),
                            tint = CakkieLightBrown
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = R.string.no_chats),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextColorDark,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(items = convos) {
                        val index = convos.indexOf(it)
                        if (index > convos.size - 2 && conversations?.data?.isEmpty() == false) {
                            viewModel.getConversations(
                                query.text,
                                conversations.meta.nextPage,
                                conversations.meta.pageSize
                            )
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator.navigate(ChatDestination(it.id, it))
                                }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                var isLoading by remember {
                                    mutableStateOf(false)
                                }
                                AsyncImage(
                                    model = it.display.image.replace(
                                        Regex("\\bhttp://"),
                                        "https://"
                                    ),
                                    contentDescription = "profile pic",
                                    onState = {
                                        //update isLoaded
                                        isLoading = it is AsyncImagePainter.State.Loading
                                    },
                                    modifier = Modifier
                                        .border(
                                            width = 2.dp,
                                            color = if (it.display.isOnline) CakkieGreen else Color.Transparent,
                                            shape = CircleShape
                                        )
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
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = it.display.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = CakkieBrown,
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        maxLines = 1,
                                        text = it.recentMessage.text,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = CakkieBrown,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = it.recentMessage.createdAt.formatDate(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextColorInactive,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                AnimatedVisibility(visible = it.unreadCount > 0) {
                                    Box(
                                        modifier = Modifier
                                            .background(CakkieBrown, CircleShape)
                                            .clip(CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = it.unreadCount.toString(),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = CakkieBackground,
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}