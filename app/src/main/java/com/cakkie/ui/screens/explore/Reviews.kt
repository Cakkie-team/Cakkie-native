package com.cakkie.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.ui.theme.TextColorInactive

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Reviews(shopId: String) {
    val reviews by remember {
        mutableStateOf(listOf<String>("", ""))
    }
    Column(Modifier.fillMaxSize()) {
        if (reviews.isEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.oh_oh),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CakkieBrown,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.sorry_no_reviews_yet),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColorInactive,
                    textAlign = TextAlign.Center
                )
            }
        }
        reviews.forEach { _ ->
            Row(
                Modifier
//                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                GlideImage(
                    model = "https://cdn.cakkie.com/imgs/Cakkie%20Icon%20(6).png",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(CakkieBrown, CircleShape)
                        .clickable {

                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = "Cake Paradise",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "8 h",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 12.sp
                            )
                        }
                        Row {
                            repeat(5) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.star_filled
                                    ),
                                    contentDescription = "star",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(4.dp),
                                    tint = if (it < 4) CakkieYellow else CakkieYellow.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                    Text(
                        text = "This is so lovely \uD83D\uDE0D \n if you haven't tried it yet, you're missing out! \uD83D\uDE0B \n" +
                                "#CakeLove #DessertParadise",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}