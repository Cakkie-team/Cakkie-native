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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination
fun ResetPassword(navigator: DestinationsNavigator) {
    var password by remember {
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
    var confirmPassword by remember {
        mutableStateOf(false)
    }

    var newPassword by remember {
        mutableStateOf(false)
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
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(id = R.string.confirm_new_password),
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(150.dp))

            if (confirmPassword == newPassword) {

            }

            CakkieButton(
                Modifier.height(50.dp),
                processing = processing,
                enabled = false,
                text = stringResource(id = R.string.continue_)
            ) {


            }

        }
    }
}