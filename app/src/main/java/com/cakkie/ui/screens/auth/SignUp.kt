package com.cakkie.ui.screens.auth

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
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination
fun SignUpScreen(email: String) {
    var FirstName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var LastName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var UserName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var Address by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var Password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }

    var ischecked by remember {
        mutableStateOf(
            false
        )
    }

    val canProceed = FirstName.text.isNotBlank() &&
            LastName.text.isNotBlank() &&
            UserName.text.isNotBlank() &&
            Address.text.isNotBlank() &&
            Password.text.isNotBlank() && !ischecked

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

                modifier = Modifier
                    .clickable {

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
            text = stringResource(id = R.string.you_are_almost_there),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(id = R.string.create_an_account_to_experience_sweet_delight),
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

        Spacer(modifier = Modifier.height(16.dp))
        CakkieInputField(
            value = Address,
            onValueChange = { Address = it },
            placeholder = stringResource(id = R.string.address_City_State),
            keyboardType = KeyboardType.Text,

            )

        Spacer(modifier = Modifier.height(16.dp))
        CakkieInputField(
            value = Password,
            onValueChange = { Password = it },
            placeholder = stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = ischecked, onCheckedChange = { ischecked = it })

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