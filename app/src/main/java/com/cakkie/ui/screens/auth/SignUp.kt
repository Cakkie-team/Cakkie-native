package com.cakkie.ui.screens.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.getCurrentLocation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination
fun SignUpScreen(email: String, navigator: DestinationsNavigator) {
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
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val context = LocalContext.current
    val activity = context as Activity
    var processing by remember {
        mutableStateOf(false)
    }

    var ischecked by remember {
        mutableStateOf(
            false
        )
    }

    val currentLocation = activity.getCurrentLocation()

    val canProceed = firstName.text.isNotBlank() &&
            lastName.text.isNotBlank() &&
            userName.text.isNotBlank() &&
            address.text.isNotBlank() &&
            password.text.isNotBlank() && !ischecked

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
                onValueChange = { firstName = it },
                placeholder = stringResource(id = R.string.firstName),
                keyboardType = KeyboardType.Text,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = stringResource(id = R.string.LastName),
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = userName,
                onValueChange = { userName = it },
                placeholder = stringResource(id = R.string.Username),
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = address,
                onValueChange = { address = it },
                placeholder = stringResource(id = R.string.address_City_State),
                keyboardType = KeyboardType.Text,
                isAddress = true,
                isEditable = false,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = ischecked,
                    onCheckedChange = { ischecked = it },
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
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.cakkie.com/terms-and-conditions")
                            )
                            context.startActivity(intent)
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
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.cakkie.com/privacy-policy")
                            )
                            context.startActivity(intent)
                        },
                        fontSize = 12.sp
                    )
                }


            }
            Spacer(modifier = Modifier.height(50.dp))

            CakkieButton(
                Modifier.height(50.dp),
                processing = processing,
                enabled = canProceed,
                text = stringResource(id = R.string.create_Account)
            ) {

                //check if the email is valid
//            isEmailValid = emailRegex.matches(input = email.text)
//            if (isEmailValid) {
//                processing = true
//                viewModel.checkEmail(email.text).addOnSuccessListener { user ->
//                    processing = false
//                    Timber.d(user.toString())
//                    //navigate to login screen
//                }.addOnFailureListener { exception ->
//                    //show toast
//                    Toaster(
//                        context = context,
//                        message = "Email not found",
//                        image = R.drawable.logo
//                    ).show()
//                    processing = false
//                    Timber.d(exception.message)
//                    //navigate to sign up screen
//                }
//            }
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