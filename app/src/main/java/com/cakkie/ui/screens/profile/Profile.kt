package com.cakkie.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.CakkieOrange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun Profile(navigator: DestinationsNavigator) {
    val msg = painterResource(id = R.drawable.msg)
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    },
            )
            Text(
                text = stringResource(id = R.string.profile),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 119.dp)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
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
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "   Uyo Nwaniba",
            style = MaterialTheme.typography.labelSmall,
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
            CakkieButton(
                text = stringResource(id = R.string.follow)
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
                    painter = msg, contentDescription = "",
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
                    imageVector = Icons.Default.Share, contentDescription = "",
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
            Column {
                Text(
                    text = "870",
                    style = MaterialTheme.typography.labelLarge
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
            Column {
                Text(
                    text = "120k",
                    style = MaterialTheme.typography.labelLarge
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
            Column {
                Text(
                    text = "354k",
                    style = MaterialTheme.typography.labelLarge
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .size(width = 100.dp, height = 34.dp)
                    .padding(start = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CakkieOrange,
                    contentColor = CakkieBrown
                ),
                shape = RoundedCornerShape(60)
            ) {
                Text(
                    text = stringResource(id = R.string.posts),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .size(width = 140.dp, height = 34.dp)
                    .padding(start = 20.dp),
                border = BorderStroke(1.dp, color = CakkieLightBrown),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CakkieBackground,
                    contentColor = CakkieBrown
                ),
                shape = RoundedCornerShape(60)
            ) {
                Text(
                    text = stringResource(id = R.string.about),
                    style = MaterialTheme.typography.labelSmall,
                    color = CakkieLightBrown
                )
            }
        }
    }
}
