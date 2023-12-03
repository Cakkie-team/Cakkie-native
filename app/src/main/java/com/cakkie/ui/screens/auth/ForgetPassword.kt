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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.OtpScreenDestination
import com.cakkie.ui.screens.destinations.SignUpScreenDestination
import com.cakkie.ui.theme.Error
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun ForgetPassword( navigator: DestinationsNavigator) {
    val viewModel: AuthViewModel = koinViewModel()
    val context = LocalContext.current
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var processing by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "arrow back",
            modifier = Modifier
                .padding(bottom = 30.dp)
        )
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
            text = stringResource(id = R.string.forgot_your_password),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.no_worries),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(40.dp))
        CakkieInputField(
            value = email,
            onValueChange = {
                isError = false
                email = it
            },
            placeholder = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email,
            isError = isError,
        )
        //show error if email is not valid
        if (isError) {
            Text(
                text = stringResource(id = R.string.sorry_email_is_incorrect),
                style = MaterialTheme.typography.bodyLarge,
                color = Error
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
            text = stringResource(id = R.string.submit),
            enabled = email.text.isNotEmpty()
        ) {
            navigator.navigate(OtpScreenDestination)
        }
        Spacer(modifier = Modifier.height(60.dp))

    }
}