package com.cakkie.ui.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun LoginScreen(email: String) {
    var password by remember {
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
            contentDescription = "cakkie logo",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.please_enter_password),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(40.dp))
        CakkieInputField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.forget_password),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.height(50.dp),
            processing = false,
            text = stringResource(id = R.string.login)
        ) {
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.do_not_have_account),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.titleMedium,
                color = CakkieBrown
            )
        }
    }
}