package com.cakkie.ui.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive
import com.cakkie.utill.formatDateTime

@Composable
fun JobsItems(
    item: JobModel,
    onClick: (() -> Unit)
) {

    Box(
        Modifier
            .clickable { onClick.invoke() }
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.6f),
            ) {
                Image(
                    painter = painterResource(
                        id = when (item.productType) {
                            "small_chops" -> R.drawable.cupcake
                            "cupcake" -> R.drawable.cupcake
                            else -> R.drawable.cupcake
                        }
                    ),
                    contentDescription = "cupcake",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(start = 5.dp)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.createdAt.formatDateTime(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = TextColorInactive
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        color = TextColorInactive,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                Modifier
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.view_to_apply),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = CakkieBrown
                )
                Text(
                    text = item.state,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 12.sp,
                    color = CakkieBrown
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}