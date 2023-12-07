package com.cakkie.ui.screens.auth


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
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


@Composable
@Destination
fun ResetPassword(email: String, navigator: DestinationsNavigator) {
    val viewModel: AuthViewModel = koinViewModel()
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }

    var confirmPassword by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val canProceed = password.text.isNotEmpty() &&
            password.text == confirmPassword.text


    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
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
                    .padding(bottom = 52.dp)
                    .size(67.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(id = R.string.reset_password),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.reset_password_message),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(52.dp))

            CakkieInputField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(id = R.string.new_password_),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(16.dp))

            CakkieInputField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = stringResource(id = R.string.confirm_new_password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(150.dp))


            CakkieButton(
                Modifier.height(50.dp),
                processing = processing,
                enabled = canProceed,
                text = stringResource(id = R.string.continue_)
            ) {
                viewModel.resetPassword(password = password.text, passwordConfirmation = confirmPassword.text).addOnSuccessListener { response ->
                    processing = false
                    //  show toast
                    Toaster(
                        context = context,
                        message = response.message,
                        image = R.drawable.logo
                    ).show()
                    //    navigate to login screen
                    navigator.navigate(
                        LoginScreenDestination(
                            email = email,
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