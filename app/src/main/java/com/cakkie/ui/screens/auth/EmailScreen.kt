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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.cakkie.ui.theme.Error
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
@Destination
fun EmailScreen(navigator: DestinationsNavigator) {
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
            text = stringResource(id = R.string.hello_there),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.enter_your_email_to_continue),
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
            text = stringResource(id = R.string.continue_)
        ) {
            //check if the email is valid
            isEmailValid = emailRegex.matches(input = email.text)
            if (isEmailValid) {
                processing = true
                viewModel.checkEmail(email.text).addOnSuccessListener { user ->
                    processing = false
//                    Timber.d(user.toString())
                    //navigate to login screen
                    navigator.navigate(LoginScreenDestination)
                }.addOnFailureListener { exception ->
                    //show toast
                    Toaster(
                        context = context,
                        message = "Email not found",
                        image = R.drawable.logo
                    ).show()
                    processing = false
                    Timber.d(exception.message)
                    //navigate to sign up screen
                }
            }
        }
    }
}