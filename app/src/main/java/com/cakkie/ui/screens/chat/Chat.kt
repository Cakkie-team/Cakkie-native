package com.cakkie.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.TextColorDark
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Chat(id: String, navigator: DestinationsNavigator) {


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
                        contentDescription = "Back", contentScale = ContentScale.FillWidth,
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

            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.options),
                    contentDescription = "Back", contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(24.dp)
                )
            }
        }
    }
}