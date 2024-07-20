package com.cakkie.ui.screens.orders


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.networkModels.Order
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.CancelOrderDestination
import com.cakkie.ui.screens.destinations.ChatDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatDateTime
import com.cakkie.utill.formatNumber
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun OrderDetails(
    id: String,
    item: Order = Order(),
    cancelResultRecipient: ResultRecipient<CancelOrderDestination, Boolean>,
    navigator: DestinationsNavigator
) {
    val viewModel: OrderViewModel = koinViewModel()
    var order by remember {
        mutableStateOf(item)
    }
    val context = LocalContext.current
    val meta = listOf(
        Pair("Size", "${order.meta.size} inches"),
        Pair("Flavour", order.meta.flavour),
        Pair("Price", "N ${formatNumber(order.unitPrice * order.quantity)}"),
        Pair("Quantity", order.quantity.toString()),
    )
    val openDialog = remember {
        mutableStateOf(false)
    }
    var showCode by remember {
        mutableStateOf(false)
    }

    var canCancel by remember {
        mutableStateOf(false)
    }

    var remainingTime by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = order.waitTime) {
        if (order.createdAt.isNotEmpty()) {
            val targetDateTime = LocalDateTime.parse(
                order.waitTime.ifEmpty { order.createdAt },
                DateTimeFormatter.ISO_DATE_TIME
            )

            // Add 2 hours to the target time
            val targetMillis =
                targetDateTime
                    .plusHours(1)
                    .plusMinutes(30)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            remainingTime = String.format("%02d:%02d:%02d", 0, 0, 0)
            while (Instant.now().toEpochMilli() < targetMillis) {
                val currentTime = Instant.now().toEpochMilli()
                val remainingMillis = targetMillis - currentTime

//            Timber.d("Remaining time: $remainingMillis")

                val days = remainingMillis / (1000 * 60 * 60 * 24)
                val hours = (remainingMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                val minutes = (remainingMillis % (1000 * 60 * 60)) / (1000 * 60)
                val seconds = (remainingMillis % (1000 * 60)) / 1000

                remainingTime = if (days > 0) {
                    String.format("%d days %02d:%02d:%02d", days, hours, minutes, seconds)
                } else {
                    String.format("%02d:%02d:%02d", hours, minutes, seconds)
                }

                delay(1000) // Delay for 1 second
            }

            // Countdown finished
            canCancel = true
        }
    }

    LaunchedEffect(id) {
        viewModel.getOrder(id)
            .addOnSuccessListener {
                order = it
            }
            .addOnFailureListener {
                Toaster(
                    context,
                    it.localizedMessage ?: "Unable to retrive order",
                    R.drawable.logo
                ).show()
            }
    }
    cancelResultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.getOrder(id)
                    .addOnSuccessListener {
                        order = it
                    }
                    .addOnFailureListener {
                        Toaster(
                            context,
                            it.localizedMessage ?: "Unable to retrive order",
                            R.drawable.logo
                        ).show()
                    }
            }
        }
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    },
            )
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            IconButton(
                onClick = {
                    navigator.navigate(ChatDestination("support"))
                }, modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.ordered_from),
                style = MaterialTheme.typography.bodyLarge,

                )
            Text(
                text = " ${order.shop.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown
            )
        }
        Text(
            text = "on ${order.createdAt.formatDateTime()}",
            style = MaterialTheme.typography.bodyLarge,
            color = TextColorDark.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            color = CakkieBrown,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = order.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        meta.filterIndexed { index, _ -> index % 2 == 0 }
            .zip(meta.filterIndexed { index, _ -> index % 2 != 0 }).forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        Text(
                            text = it.first.first,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = it.first.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = it.second.first,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = it.second.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }

        Spacer(modifier = Modifier.height(5.dp))
        Column {
            Text(
                text = stringResource(id = R.string.delivery_address),
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(
                        id = R.drawable.location
                    ),
                    contentDescription = "location",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = order.deliveryAddress,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Icon(
                        painter = painterResource(
                            id = when (order.status.lowercase()) {
                                "pending" -> R.drawable.done
                                "cancelled" -> R.drawable.cancel
                                "completed" -> R.drawable.approved
                                "declined" -> R.drawable.cancel
                                else -> R.drawable.progress
                            }
                        ),
                        contentDescription = "",
                        tint = CakkieBrown
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = order.status.lowercase(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.waiting_time),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = remainingTime,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CakkieButton(
                text = stringResource(
                    id = when (order.status) {
                        "PENDING" -> R.string.cancel
                        else -> R.string.generate_code
                    }
                ),
                enabled = (order.status == "PENDING" && canCancel) || order.status == "ARRIVED",
            ) {
                if (order.status == "PENDING") {
                    openDialog.value = true
                } else {
                    showCode = true
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = id.takeLast(6),
                style = MaterialTheme.typography.bodyLarge,
                color = CakkieBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .blur(if (showCode) 0.dp else 10.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Reveal code to delivery agent only when you receive your order",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
        )
    }

    if (openDialog.value)
        Popup(
            onDismissRequest = { openDialog.value = false },
            alignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = CakkieBrown,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.8f)
//                    .padding(horizontal = 16.dp)
                    .background(CakkieBackground, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cakkie_icon),
                            contentDescription = ""
                        )
                        Text(
                            text = stringResource(id = R.string.warning),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.are_you_sure_you_want_to_cancel_this_order),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row {
                        Button(
                            onClick = { openDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = CakkieBrown,
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.no),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        Button(
                            onClick = {
                                openDialog.value = false
                                navigator.navigate(CancelOrderDestination(id))
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = CakkieBrown,
                                containerColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
}


