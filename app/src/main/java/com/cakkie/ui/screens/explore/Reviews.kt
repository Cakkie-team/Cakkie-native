package com.cakkie.ui.screens.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.theme.CakkieYellow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Reviews() {
    Column {
        repeat(5) {
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                GlideImage(
                    model = "https://source.unsplash.com/60x60/?profile",
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
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
                                fontSize = 12.sp
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
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}