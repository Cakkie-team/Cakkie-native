package com.cakkie.ui.screens.cakespiration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.screens.destinations.CommentDestination
import com.cakkie.ui.screens.destinations.MoreOptionsDestination
import com.cakkie.ui.screens.destinations.ProfileDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieYellow
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CakespirationItem(navigator: DestinationsNavigator) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var isLiked by remember { mutableStateOf(false) }
    var isStarred by remember { mutableStateOf(false) }
    var maxLines by remember { mutableIntStateOf(1) }
    Box(
        modifier = Modifier
            .background(Color.Black)
            .height(screenHeight - 42.dp),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = "https://source.unsplash.com/400x750/?cake?video",
            contentDescription = "cake video",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
                .offset(x = (-14).dp, y = (-16).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(
                    id =
                    if (isLiked) R.drawable.gridicons_heart else R.drawable.heart
                ),
                contentDescription = "heart",
                tint = if (isLiked) Color.Red else CakkieBackground,
                modifier = Modifier
                    .clickable {
                        isLiked = !isLiked
                    }
                    .padding(8.dp)
            )
            Text(
                text = "1.2k",
                color = CakkieBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.comment),
                contentDescription = "comment",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { navigator.navigate(CommentDestination) }
                    .padding(8.dp)
            )
            Text(
                text = "1.2k",
                color = CakkieBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "share",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { }
                    .padding(8.dp)
            )
            Icon(
                painter = painterResource(
                    id =
                    if (isStarred) R.drawable.star_filled else R.drawable.star_add
                ),
                contentDescription = "star",
                tint = if (isStarred) CakkieYellow else CakkieBackground,
                modifier = Modifier
                    .clickable {
                        isStarred = !isStarred
                    }
                    .padding(8.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.options),
                contentDescription = "more",
                tint = CakkieBackground,
                modifier = Modifier
                    .clickable { navigator.navigate(MoreOptionsDestination) }
                    .padding(8.dp)
            )
        }

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp)
                .offset(x = 16.dp, y = (-16).dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = "https://source.unsplash.com/60x60/?profile",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navigator.navigate(ProfileDestination)
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Cake Paradise",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = CakkieBackground
                    )
                    Text(
                        text = "8 hours ago",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = CakkieBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Indulge in the best cakes in town with Cake Paradise and get 10% off on your first order!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(0.8f),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                color = CakkieBackground
            )
            if (maxLines == 1) {
                Text(
                    text = stringResource(id = R.string.see_more),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clickable { maxLines = Int.MAX_VALUE },
                    color = CakkieBackground
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth(0.6f)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(40))
                    .clip(RoundedCornerShape(40)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.music),
                    contentDescription = "music",
                    modifier = Modifier
                        .padding(6.dp)
                        .size(18.dp)
                )
                Text(
                    text = "Imagine Dragons - Believer",
                    style = MaterialTheme.typography.bodySmall,
                    color = CakkieBackground,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}