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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
    val user = viewModel.user.observeAsState().value
    var processing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pin by remember { mutableStateOf(TextFieldValue("")) }
    var pinConfirm by remember { mutableStateOf(TextFieldValue("")) }
    var step by remember { mutableIntStateOf(0) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = user?.pin) {
        step = if (user?.pin == null) 0
        else 1
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 17.dp)
    ) {

        Text(
            text = stringResource(
                id = when (step) {
                    0 -> R.string.create_pin
                    1 -> R.string.Confirm_Pin
                    else -> R.string.otp_verification
                }
            ),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Text(
            text = stringResource(
                id = when (step) {
                    0 -> R.string.enter_transaction_pin
                    1 -> R.string.confirm_transaction_pin
                    else -> R.string.kindly_enter_the_verification_code_sent_to_your_email
                }, user?.email ?: ""
            ),
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
        OtpInput(
            value = when (step) {
                0 -> pin
                1 -> pinConfirm
                else -> otp
            }, onValueChange = {}, readOnly = true
        )
        listOf(1, 4, 7).forEach { row ->
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (0..2).forEach { col ->
                    Card(
                        onClick = {
                            when (step) {
                                0 -> pin =
                                    if (pin.text.length < 4) pin.copy(text = pin.text + (row + col).toString()) else pin

                                1 -> pinConfirm =
                                    if (pinConfirm.text.length < 4) pinConfirm.copy(text = pinConfirm.text + (row + col).toString()) else pinConfirm

                                else -> otp =
                                    if (otp.text.length < 4) otp.copy(text = otp.text + (row + col).toString()) else otp
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
                        when (step) {
                            0 -> pin = if (col == 1) pin.copy(text = pin.text.dropLast(1))
                            else if (pin.text.length < 4) pin.copy(text = pin.text + "0") else pin

                            1 -> pinConfirm =
                                if (col == 1) pinConfirm.copy(text = pinConfirm.text.dropLast(1))
                                else if (pinConfirm.text.length < 4) pinConfirm.copy(text = pinConfirm.text + "0") else pinConfirm

                            else -> otp = if (col == 1) otp.copy(text = otp.text.dropLast(1))
                            else if (otp.text.length < 4) otp.copy(text = otp.text + "0") else otp
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
        if (step == 0) {
            step = 1
            return@CakkieButton
        }
        if (step == 1) {
            if (pin.text == pinConfirm.text) {
                step = 2
                return@CakkieButton
            }
        }
        if (step == 2) {
//            viewModel.verifyOtp(otp.text)
            return@CakkieButton
        }
        processing = false
//        onComplete.navigateBack(result = true)
        processing = true

    }
    Spacer(modifier = Modifier.height(17.dp))

}