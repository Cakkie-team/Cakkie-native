package com.cakkie.ui.screens.wallet.bottomUI

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.SolanaPayBt
import com.cakkie.ui.screens.destinations.BrowserDestination
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.ui.theme.TextColorInactive
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun BuyIcing(
    navigator: DestinationsNavigator,
    onComplete: ResultRecipient<BrowserDestination, Boolean>
) {
    val viewModel: WalletViewModel = koinViewModel()
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    val user = viewModel.user.observeAsState().value

    onComplete.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                navigator.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 17.dp)
    ) {

        Text(
            text = stringResource(id = R.string.buy_icing),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Text(
            text = stringResource(id = R.string.buy_icing_message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )
    }
    Spacer(modifier = Modifier.height(15.dp))

    Row(
        Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, color = CakkieBrown, shape = MaterialTheme.shapes.small
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "ICING",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColorInactive,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = NumberFormat.getInstance().format(
                        amount.text.ifEmpty { "0" }.toInt()
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (amount.text.isEmpty()) TextColorInactive else TextColorDark,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(26.dp))

    SolanaPayBt(enabled = amount.text.isNotEmpty()) {
        navigator.navigate(
            BrowserDestination(
                "https://cakkie.com/solana-pay?desc=${"cakkie"}&userId=${user?.id}&currencySymbol=${"ICING"}&price=${
                    amount.text
                }"
            )
        )
    }
    Spacer(modifier = Modifier.height(17.dp))

}