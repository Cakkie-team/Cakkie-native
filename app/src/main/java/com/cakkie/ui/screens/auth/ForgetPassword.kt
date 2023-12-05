package com.cakkie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.cakkie.ui.screens.destinations.LoginScreenDestination
import com.cakkie.ui.screens.destinations.OtpScreenDestination
import com.cakkie.ui.screens.destinations.ResetPasswordDestination
import com.cakkie.ui.screens.destinations.SignUpScreenDestination
import com.cakkie.ui.theme.Error
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
@Destination
fun ForgetPassword(navigator: DestinationsNavigator) {
    val viewModel: AuthViewModel = koinViewModel()
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    //email regex
    val emailRegex = Regex(pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

    //to check if the email is valid
    var isEmailValid by remember {
        mutableStateOf(true)
    }
    var processing by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Arrow Back",

            modifier = Modifier
                .clickable {
                    navigator.popBackStack()
                }
                .size(24.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Cakkie Logo",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.forget_password),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.no_worries),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(40.dp))
        CakkieInputField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email,
            isError = !isEmailValid
        )
        //show error if email is not valid
        if (!isEmailValid) {
            Text(
                text = stringResource(id = R.string.please_enter_a_valid_email),
                style = MaterialTheme.typography.bodyLarge,
                color = Error
            )
        }
        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
            enabled = email.text.isNotEmpty(),
            text = stringResource(id = R.string.submit)
        ) {
            //check if the email is valid
            isEmailValid = emailRegex.matches(input = email.text)
            if (isEmailValid) {
                processing = true
                viewModel.forgetPassword(email.text).addOnSuccessListener { response ->
                    processing = false
                    //  show toast
                    Toaster(
                        context = context,
                        message = response.message,
                        image = R.drawable.logo
                    ).show()
                    //    navigate to otp screen
                    navigator.navigate(
                        OtpScreenDestination(
                            email = email.text,
                            isNewDevice = false
                        )
                    )
                }.addOnFailureListener { exception ->
                    //  show toast
                    Toaster(
                        context = context,
                        message = exception,
                        image = R.drawable.logo
                    ).show()
                    processing = false
                    Timber.d(exception)
                }
            }
        }
    }
}