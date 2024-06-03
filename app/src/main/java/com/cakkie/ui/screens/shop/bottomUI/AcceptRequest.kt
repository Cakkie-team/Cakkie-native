package com.cakkie.ui.screens.shop.bottomUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.DateTimePicker
import com.cakkie.ui.screens.orders.OrderViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun AcceptRequest(id: String, onComplete: ResultBackNavigator<Boolean>) {
    val viewModel: OrderViewModel = koinViewModel()
    var reason by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }
    val currentDate = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        selectedDate.add(Calendar.HOUR_OF_DAY, 12)
    }
    val diffInMillis = selectedDate.timeInMillis - currentDate.timeInMillis

    val totalHours = diffInMillis / (1000 * 60 * 60)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(80.dp)
                .height(8.dp)
                .clip(CircleShape)
                .background(CakkieBrown)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.accept_request),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Text(
            text = stringResource(id = R.string.if_you_accept_request),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.how_long_will_this_take),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(4.dp))
        DateTimePicker(
            label = "Select Date and Time",
            selectedDate = selectedDate,
            onDateTimeSelected = { newDate ->
                selectedDate = newDate
            }
        )

    }
    Spacer(modifier = Modifier.height(15.dp))

    CakkieButton(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        text = stringResource(id = R.string.accept_request),
        processing = processing,
        enabled = reason.text.isNotEmpty()
    ) {
        processing = true
        viewModel.declineOrder(id, reason.text)
            .addOnSuccessListener {
                processing = false
                Toaster(
                    context = context,
                    message = "Request accepted Successfully!",
                    image = R.drawable.logo
                ).show()
                onComplete.navigateBack(result = true)
            }
            .addOnFailureListener {
                processing = false
                Toaster(
                    context = context,
                    message = "Failed to accept request, please contact support!",
                    image = R.drawable.logo
                ).show()
            }
    }
    Spacer(modifier = Modifier.height(17.dp))

}