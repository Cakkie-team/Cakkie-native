package com.cakkie.ui.screens.auth

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.Error
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber


@Composable
@Destination
fun SignUpScreen() {

    var FirstName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var LastName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var UserName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var Password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(

           contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier.clickable {

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



        Text(
            text = stringResource(id = R.string.You_are_almost_there),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.Create_an_account_to_experience_sweet_delight),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(28.dp))
        CakkieInputField(
            value = FirstName,
            onValueChange = { FirstName = it },
            placeholder = stringResource(id = R.string.firstName),
            keyboardType = KeyboardType.Text,

            )
        Spacer(modifier = Modifier.height(16.dp))
        CakkieInputField(
            value = LastName,
            onValueChange = { LastName = it },
            placeholder = stringResource(id = R.string.LastName),
            keyboardType = KeyboardType.Text,

            )

        Spacer(modifier = Modifier.height(16.dp))
        CakkieInputField(
            value = UserName,
            onValueChange = { UserName = it },
            placeholder = stringResource(id = R.string.Username),
            keyboardType = KeyboardType.Text,

            )

        Spacer(modifier = Modifier.weight(0.3f))
        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
//            enabled = email.text.isNotEmpty(),
            text = stringResource(id = R.string.Create_Account)
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
    }
}