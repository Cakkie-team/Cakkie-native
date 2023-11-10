package com.cakkie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Composable
@Destination
fun OtpScreen(email: String, isNewDevice: Boolean, navigator: DestinationsNavigator) {
    //countdown timer
    var timer by remember {
        mutableIntStateOf(60)
    }

    var timerRunning by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = timerRunning) {
        timer = 60
        if (timerRunning) {
            while (timer > 0) {
                timer--
                delay(1000)
            }
            if (timer == 0) timerRunning = false
        }
    }

    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Cakkie Logo",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(160.dp)
                .align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(
                id = if (isNewDevice) R.string.new_device_detected else R.string.otp_verification
            ), style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(
                id = R.string.kindly_enter_the_verification_code_sent_to_your_email, email
            ) + if (isNewDevice) " " + stringResource(id = R.string.to_register_device) else "",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.height(50.dp),
            processing = false,
            text = stringResource(id = R.string.continue_)
        ) {

        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.did_not_get_code),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = if (timerRunning) timer.toString() + "s"
        else stringResource(id = R.string.resend_code),
            style = MaterialTheme.typography.titleMedium,
            color = CakkieBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(CenterHorizontally)
                .clickable {
                    if (!timerRunning) {
                        timerRunning = true
                    }
                })

    }
}