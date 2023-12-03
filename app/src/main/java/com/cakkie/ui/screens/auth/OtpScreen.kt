package com.cakkie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun OtpScreen() {
    var email = remember {
        mutableStateOf(TextFieldValue(""))
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
            text = stringResource(id = R.string.otp_verification),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(40.dp))
        CakkieInputField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.height(50.dp),
            processing = false,
            text = stringResource(id = R.string.continue_)
        ) {

        }
    }
}