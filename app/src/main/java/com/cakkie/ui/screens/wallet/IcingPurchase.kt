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
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun IcingPurchase (){
    var amount by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column (
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
            text = stringResource(id = R.string.icing_purchase),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.purchase_some_icing_with_some_naira_here),
            style = MaterialTheme.typography.bodySmall,
            color = TextColorDark.copy(0.7f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.purchase_limit),
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(0.7f)
            )
            Text(
                text = "NGN11,000",
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(0.7f)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        CakkieInputField(value = amount,
            onValueChange = {
                                                     amount = it},

            placeholder = stringResource(id = R.string.amount_of_icing),
            keyboardType = KeyboardType.Number)
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
        Text(text = "Respective amount of Naira: NGN 0",
            style = MaterialTheme.typography.bodySmall,
            color = TextColorDark.copy(0.5f)
        )
            Spacer(modifier = Modifier.width(3.dp))
            Image(painter = painterResource(id = R.drawable.badge), contentDescription = "",
                modifier = Modifier.size(10.dp)
            )
    }
        Spacer(modifier = Modifier.fillMaxHeight())
        CakkieButton(text = stringResource(id = R.string.proceed)) {
            
        }
        }
}