package com.cakkie.ui.screens.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WalletWithdrawal () {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var bank by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var accountnumber by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(painter = painterResource(id = R.drawable.arrow_back), contentDescription = "")
            Text(
                text = stringResource(id = R.string.wallet),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(id = R.string.withdrawal),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.withdraw_some_naira_into_your_bank_account_here),
            style = MaterialTheme.typography.bodySmall,
            color = TextColorDark.copy(0.7f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.withdrawal_limit),
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(0.7f)
            )
            Text(
                text = "NGN11,000",
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(0.7f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CakkieInputField(
            value = email,
            onValueChange = {
                email = it
            },

            placeholder = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(10.dp))
        CakkieInputField(
            value = bank,
            onValueChange = {
                bank = it
            },

            placeholder = stringResource(id = R.string.bank),
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(10.dp))
        CakkieInputField(
            value = accountnumber,
            onValueChange = {
                accountnumber = it
            },

            placeholder = stringResource(id = R.string.account_number),
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(180.dp))
        CakkieButton(text = stringResource(id = R.string.proceed)) {

        }
    }
}