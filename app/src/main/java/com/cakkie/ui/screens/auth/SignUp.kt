package com.cakkie.ui.screens.auth

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.BrowserDestination
import com.cakkie.ui.screens.destinations.OtpScreenDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.cakkie.utill.getCurrentLocation
import com.cakkie.utill.locationModels.LocationResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


@Composable
@Destination
fun SignUpScreen(email: String, navigator: DestinationsNavigator) {
    val viewModel: AuthViewModel = koinViewModel()
    var firstName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var lastName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var userName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var address by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var referralCode by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var location by remember {
        mutableStateOf<LocationResult?>(null)
    }

    var isUsernameTaken by remember {
        mutableStateOf(false
        )
    }

    val context = LocalContext.current
    val activity = context as Activity
    var processing by remember {
        mutableStateOf(false)
    }

    var isChecked by remember {
        mutableStateOf(
            false
        )
    }

    val currentLocation = activity.getCurrentLocation()
    LaunchedEffect(userName.text) {
        if (userName.text.isNotBlank()) {
          delay(500L)
            viewModel.checkUserName(userName.text)
                .addOnSuccessListener { isFree ->
                    Timber.d("Username availability: ${isFree.username}")
//                    isUsernameTaken = !isFree.username.equals(userName.text, ignoreCase = true)
                    isUsernameTaken = true
                }
                .addOnFailureListener { exception ->
                    Timber.d("Username check failed: $exception")
                    isUsernameTaken = false // Assume taken if check fails
                }
        }
    }
    val canProceed = firstName.text.isNotBlank() &&
            lastName.text.isNotBlank() &&
            userName.text.isNotBlank() &&
            address.text.isNotBlank() &&
            password.text.isNotBlank() && isChecked



    LaunchedEffect(key1 = Unit) {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {

            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // Connection established.
                        Timber.d("Connection established")

                        val response: ReferrerDetails = referrerClient.installReferrer
                        val referrerUrl: String = response.installReferrer
                        Timber.d("referrerUrl - $referrerUrl")//referrerUrl - utm_source=google-play&utm_medium=organic //it's default value only

                        if (referrerUrl.isNotEmpty()) {
                            val referrerParts = referrerUrl.split("&")//split with &
                            Timber.d(
                                "referrerParts - %s",
                                referrerParts
                            )//divided in 2 parts utm_source,utm_content

                            val utmSource = referrerParts.find {
                                it.contains("utm_source")
                            }?.split("=")?.get(1)//get the value of utm_source
                            Timber.d("utmSource - $utmSource")

                            if (utmSource != null && utmSource == "refer") {
                                val utmContent = referrerParts.find {
                                    it.contains("utm_content")
                                }?.split("=")?.get(1)//get the value of utm_content
                                Timber.d("utmContent - $utmContent")

                                if (utmContent != null) {
                                    Timber.d("refCode - $utmContent")
                                    referralCode = TextFieldValue(utmContent)
                                }
                            }
                        }
                        referrerClient.endConnection()
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not available on the current Play Store app.
                        Timber.d("API not available on the current Play Store app.")
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                        Timber.d("Connection couldn't be established.")
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Timber.d("Try to restart the connection on the next request to Google Play by calling the startConnection() method.")
            }
        })
    }

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
                text = stringResource(id = R.string.you_are_almost_there),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.create_an_account_to_experience_sweet_delight),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(28.dp))
            CakkieInputField(
                value = firstName,
                onValueChange = { firstName = it.copy(text = it.text.trim()) },
                placeholder = stringResource(id = R.string.firstName),
                keyboardType = KeyboardType.Text,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = lastName,
                onValueChange = { lastName = it.copy(text = it.text.trim()) },
                placeholder = stringResource(id = R.string.LastName),
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = userName,
                onValueChange = {
                    userName = it.copy(text = it.text.trim())
                    isUsernameTaken = false
//                    if (userName.text.isNotBlank()) {
//                        // Check if the username is available
//                        viewModel.checkUserName(userName.text.lowercase())
//                            .addOnSuccessListener { isFree ->
//                                isUsernameTaken = !isUsernameTaken
//                                // Set to true if not free
//                                Timber.d("Username available: $isFree")
//                            }
//                            .addOnFailureListener { exception ->
//                                Timber.d("Username check failed: $exception")
//                                isUsernameTaken = true // Assume taken if check fails
//                            }
//                    }
                },
                placeholder = stringResource(id = R.string.Username),
                keyboardType = KeyboardType.Text,
            )
            if (isUsernameTaken && userName.text.isNotBlank()) {
                Text(
                    text = stringResource(id = R.string.username_taken),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = address,
                onValueChange = { address = it.copy(text = it.text.trim()) },
                placeholder = stringResource(id = R.string.address_City_State),
                keyboardType = KeyboardType.Text,
                isAddress = true,
                isEditable = false,
                location = currentLocation,
                onLocationClick = {
                    location = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = password,
                onValueChange = { password = it.copy(text = it.text.trim()) },
                placeholder = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = referralCode,
                onValueChange = { referralCode = it.copy(text = it.text.trim()) },
                placeholder = stringResource(id = R.string.refferal_code),
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = CakkieBrown,
                        uncheckedColor = CakkieBrown,
                        checkmarkColor = CakkieBackground
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.i_agree_to),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = R.string.terms_of_Service),
                        color = CakkieBrown,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.clickable {
                            navigator.navigate(
                                BrowserDestination(
                                    url = "https://www.cakkie.com/terms-and-conditions",

                                )
                            ) {
                                launchSingleTop = true
                            }
                        },
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = R.string.and),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = R.string.privacy_Policy),
                        color = CakkieBrown,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.clickable {
                            navigator.navigate(
                                BrowserDestination(
                                    url = "https://www.cakkie.com/privacy-policy",

                                    )
                            ) {
                                launchSingleTop = true
                            }

                        },
                        fontSize = 12.sp
                    )
                }


            }
            Spacer(modifier = Modifier.height(50.dp))

            CakkieButton(
                Modifier.fillMaxWidth(),
                processing = processing,
                enabled = canProceed,
                text = stringResource(id = R.string.create_Account)
            ) {

                // check if the email is valid
                processing = true
                viewModel.signUp(
                    email = email,
                    password = password.text,
                    firstName = firstName.text,
                    lastName = lastName.text,
                    userName = userName.text,
                    address = address.text,
                    location = location!!,
                    referralCode = referralCode.text
                ).addOnSuccessListener { resp ->
                    processing = false
                    if (resp.token.isNotEmpty()) {
                        viewModel.saveToken(resp.token)
                    }
                    Timber.d(resp.toString())
                    //navigate to home screen
                    navigator.navigate(
                        OtpScreenDestination(
                            email = email,
                            isNewDevice = false,
                            isSignUp = true
                        )
                    ) {
                        launchSingleTop = true
                    }
                }.addOnFailureListener { exception ->
                    //show toast
                    Toaster(
                        context = context,
                        message = exception,
                        image = R.drawable.logo
                    ).show()
                    processing = false
                    Timber.d(exception)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(id = R.string.login),
                    color = CakkieBrown,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable { })
            }
        }
    }
}