package com.cakkie.ui.screens.wallet.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.networkModels.Transaction
import com.cakkie.ui.screens.orders.DashDivider
import com.cakkie.ui.theme.BackgroundImageId
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.formatDate
import com.cakkie.utill.formatDateTime
import java.text.DecimalFormat

@Composable
fun Receipt(item: Transaction, onCancel: () -> Unit) {
    val dec = DecimalFormat("#,##0.00")
    Box(
        modifier = Modifier
            .background(CakkieBackground, MaterialTheme.shapes.medium)
            .border(width = 2.dp, color = CakkieBrown, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(0.8f),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painter = painterResource(id = BackgroundImageId(isSystemInDarkTheme())),
            contentDescription = "background",
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .height(500.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 37.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cakkie_icon),
                    contentDescription = "icon",
                )
                Text(
                    text = stringResource(id = R.string.reciept),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            DashDivider(
                thickness = 1.dp,
                color = CakkieBrown,
                modifier = Modifier
                    .fillMaxWidth()
            )
//            Spacer(modifier = Modifier.height(20.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = stringResource(id = R.string.item_purchased),
//                    style = MaterialTheme.typography.bodyLarge,
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                Text(
//                    text = "Icing",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = TextColorDark.copy(alpha = 0.5f)
//                )
//            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.transaction_id),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.id.take(10),
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.transaction_date),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.createdAt.formatDate(),
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.transatcion_time),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.createdAt.formatDateTime().split(",")[1],
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.name_of_reciepient),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.user.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.amount),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = dec.format(item.amount) + " " + item.currency.symbol,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.status,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (item.status == "SUCCESS") CakkieBrown else CakkieBrown.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onCancel.invoke()
                    }
                )
//                Spacer(modifier = Modifier.weight(1f))
//                Text(
//                    text = stringResource(id = R.string.download),
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = CakkieBrown,
//                    modifier = Modifier.clickable { }
//                )
            }
        }
    }
}