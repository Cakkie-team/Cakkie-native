package com.cakkie.ui.screens.wallet.bottomUI


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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.OtpInput
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ConfirmPin(
    amount: String,
    navigator: DestinationsNavigator,
    onComplete: ResultBackNavigator<Boolean>
) {
    val viewModel: WalletViewModel = koinViewModel()
    var processing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pin by remember { mutableStateOf(TextFieldValue("")) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 17.dp)
    ) {

        Text(
            text = stringResource(id = R.string.Confirm_Pin),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Text(
            text = stringResource(id = R.string.enter_transaction_pin),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
    }
    Text(
        text = "-$amount",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = 32.dp),
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp
    )
    Spacer(modifier = Modifier.height(15.dp))

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OtpInput(value = pin, onValueChange = {

        }, readOnly = true)
        listOf(1, 4, 7).forEach { row ->
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (0..2).forEach { col ->
                    Card(
                        onClick = {
                            if (pin.text.length < 4) {
                                pin = pin.copy(text = pin.text + (row + col))
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CakkieBackground
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),
                        shape = CardDefaults.elevatedShape
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                text = (row + col).toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.align(Alignment.Center),
                                color = TextColorDark,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            (0..1).forEach { col ->
                Card(
                    onClick = {
                        pin = if (col == 1) pin.copy(text = pin.text.dropLast(1))
                        else if (pin.text.length < 4) pin.copy(text = pin.text + "0") else pin
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = CardDefaults.elevatedShape
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            text = if (col == 0) "0" else "c",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.Center),
                            color = TextColorDark,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(26.dp))
    CakkieButton(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        text = stringResource(id = R.string.proceed),
        enabled = pin.text.isNotEmpty(),
        processing = processing
    ) {
//        onComplete.navigateBack(result = true)
        processing = true

    }
    Spacer(modifier = Modifier.height(17.dp))

}