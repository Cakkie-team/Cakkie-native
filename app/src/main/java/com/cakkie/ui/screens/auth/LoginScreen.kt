package com.cakkie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.EmailScreenDestination
import com.cakkie.ui.screens.destinations.ExploreScreenDestination
import com.cakkie.ui.screens.destinations.ForgetPasswordDestination
import com.cakkie.ui.screens.destinations.LoginScreenDestination
import com.cakkie.ui.screens.destinations.OtpScreenDestination
import com.cakkie.ui.screens.destinations.SignUpScreenDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun LoginScreen(email: String, navigator: DestinationsNavigator) {
    val viewModel: AuthViewModel = koinViewModel()
    val context = LocalContext.current
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var processing by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var message by remember {
        mutableStateOf("")
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
            onValueChange = {
                isError = false
                password = it
            },
            placeholder = stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password,
            isError = isError,
        )
        //show error if email is not valid
        if (isError) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.forget_password),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .height(48.dp)
                .align(Alignment.End)
                .clickable { navigator.navigate(ForgetPasswordDestination(email)) }
        )

        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.fillMaxWidth(),
            processing = processing,
            text = stringResource(id = R.string.login),
            enabled = password.text.isNotEmpty()
        ) {
            processing = true
            viewModel.login(email, password.text)
                .addOnSuccessListener {
                    processing = false
                    if (it.token.isNotEmpty()) {
                        viewModel.saveToken(it.token)
                    }
                    if (it.isNewDevice) {
                        Toaster(
                            context = context,
                            message = it.message,
                            image = R.drawable.logo
                        ).show()
                        navigator.navigate(OtpScreenDestination(email = email, isNewDevice = true))
                    } else if (it.token.isEmpty()) {
                        Toaster(
                            context = context,
                            message = it.message,
                            image = R.drawable.logo
                        ).show()
                        navigator.navigate(
                            OtpScreenDestination(
                                email = email,
                                isNewDevice = false,
                                isSignUp = true
                            )
                        )
                    } else {
                        Toaster(
                            context = context,
                            message = "Login Success",
                            image = R.drawable.logo
                        ).show()
                        navigator.navigate(ExploreScreenDestination) {
                            popUpTo(EmailScreenDestination.route) {
                                inclusive = true
                            }
                            popUpTo(LoginScreenDestination.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }

                }.addOnFailureListener {
                    processing = false
                    isError = true
                    message = it
                    Toaster(
                        context = context,
                        message = "Login Failed",
                        image = R.drawable.logo
                    ).show()
                }
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
                color = CakkieBrown,
                modifier = Modifier.clickable {
                    //navigate to sign up screen
                    navigator.navigate(SignUpScreenDestination(email))
                }
            )
        }
    }
}