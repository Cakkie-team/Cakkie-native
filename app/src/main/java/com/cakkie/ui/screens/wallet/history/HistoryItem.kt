package com.cakkie.ui.screens.wallet.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.networkModels.Transaction
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieGreen
import com.cakkie.ui.theme.CakkieYellow
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.formatDateTime
import java.text.DecimalFormat

@Composable
fun HistoryItem(item: Transaction) {
    val dec = DecimalFormat("#,##0.00")
    var showReceipt by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .clickable { showReceipt = true }
            .fillMaxWidth()
            .background(Color.White)
            .height(65.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.group),
            contentDescription = "",
            modifier = Modifier.background(
                color = CakkieBackground,
                shape = RoundedCornerShape(50)
            )
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.createdAt.formatDateTime(),
                style = MaterialTheme.typography.bodyMedium,
                color = TextColorDark.copy(0.5f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = dec.format(item.amount) + " " + item.currency.symbol,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold

            )
            Text(
                text = item.status,
                style = MaterialTheme.typography.bodyMedium,
                color = if (item.status == "SUCCESS") CakkieGreen else CakkieYellow
            )
        }
    }

    if (showReceipt) {
        Popup(
            alignment = Alignment.Center,
            offset = IntOffset(0, -100),
            onDismissRequest = { showReceipt = false },
        ) {
            Receipt(item) {
                showReceipt = false
            }
        }
    }
}