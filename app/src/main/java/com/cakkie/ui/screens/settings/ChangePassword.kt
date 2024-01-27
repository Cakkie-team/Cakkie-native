package com.cakkie.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.auth.AuthViewModel
import com.cakkie.ui.screens.destinations.ForgetPasswordDestination
import com.cakkie.ui.screens.destinations.LoginScreenDestination
import com.cakkie.ui.screens.destinations.OtpScreenDestination
import com.cakkie.ui.screens.destinations.ResetPasswordDestination
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


@Composable
@Destination
fun ChangePassword(email: String, navigator: DestinationsNavigator) {
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
      var newPassword by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var retypePassword by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val canProceed = password.text.isNotEmpty() &&
            newPassword.text == retypePassword.text


    Column(
        Modifier
            .padding(vertical = 11.dp, horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier
                    .clickable {
                        navigator.popBackStack()
                    }
                    .align(Alignment.TopStart)
                    .size(24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Cakkie Logo",
                modifier = Modifier
                    .padding(top = 52.dp)
                    .size(160.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(id = R.string.change_your_password),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.change_password_message),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(34.dp))

            CakkieInputField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(10.dp))

            CakkieInputField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = stringResource(id = R.string.new_password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(10.dp))


            CakkieInputField(
                value = retypePassword,
                onValueChange = { retypePassword = it },
                placeholder = stringResource(id = R.string.retype_password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(26.dp))


            CakkieButton(
                Modifier
                    .height(50.dp)
                    .width(328.dp),
                enabled = canProceed,
                text = stringResource(id = R.string.submit)
            ) {
                navigator.navigate(OtpScreenDestination(
                    email = email,
                    isNewDevice = false,
                    isSignUp = false,
                    isSavedChanges = true
                ))
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = stringResource(id = R.string.forgot_password),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        navigator.navigate(ForgetPasswordDestination(""))
                    }
            )

        }
    }
}