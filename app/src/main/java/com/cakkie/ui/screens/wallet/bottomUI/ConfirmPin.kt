package com.cakkie.ui.screens.wallet.bottomUI


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.cakkie.R
import com.cakkie.networkModels.CurrencyRate
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.components.OtpInput
import com.cakkie.ui.screens.destinations.BrowserDestination
import com.cakkie.ui.screens.wallet.WalletViewModel
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatNumber
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ConfirmPin(
    currencyRate: CurrencyRate,
    onComplete: ResultBackNavigator<CurrencyRate>,
    onDone: ResultRecipient<BrowserDestination, Boolean>,
    navigator: DestinationsNavigator
) {
    val viewModel: WalletViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    var processing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pin by remember { mutableStateOf(TextFieldValue("")) }
    var pinConfirm by remember { mutableStateOf(TextFieldValue("")) }
    var step by remember { mutableIntStateOf(0) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }
    var currencies by remember { mutableStateOf(emptyList<CurrencyRate>()) }
    var currency by remember { mutableStateOf(currencyRate) }
    var onSelectCurrency by remember { mutableStateOf(false) }
    var coupon by remember { mutableStateOf(TextFieldValue("")) }
    var onAddCoupon by remember { mutableStateOf(false) }
    var couponAmount by remember { mutableDoubleStateOf(0.0) }

    //countdown timer
    var timer by remember {
        mutableIntStateOf(60)
    }

    var timerRunning by remember {
        mutableStateOf(true)
    }

    val enabled = when (step) {
        0 -> pin.text.length == 4
        1 -> if (pin.text.isNotEmpty()) pin.text == pinConfirm.text
        else pinConfirm.text.length == 4

        else -> otp.text.length == 4
    }

    LaunchedEffect(key1 = onSelectCurrency) {
        viewModel.getConversionRate(
            currencyRate.symbol,
            currencyRate.amount.replace(",", "").toDouble()
        ).addOnSuccessListener {
            currencies = it
        }
    }

    onDone.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                currency = currencies.find { it.symbol == "ICING" } ?: currency
                onComplete.navigateBack(
                    currency.copy(
                        pin = pinConfirm.text,
                        symbol = currency.symbol,
                        coupon = coupon.text
                    )
                )
            }
        }
    }

    LaunchedEffect(key1 = timerRunning) {
        timer = 60
        if (timerRunning) {
            while (timer > 0) {
                timer--
                delay(1000)
            }
            if (timer == 0) timerRunning = false
        }
    }
    LaunchedEffect(key1 = user?.pin) {
        step = if (user?.pin == null) 0
        else 1
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 17.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                text = "Change Currency",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable { onSelectCurrency = true },
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = CakkieBrown,
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline,
            )

        }
        Text(
            text = stringResource(
                id = when (step) {
                    0 -> R.string.enter_transaction_pin
                    1 -> R.string.confirm_transaction_pin
                    else -> R.string.kindly_enter_the_verification_code_sent_to_your_email
                }, "${
                    user?.email?.take(4)
                }****${
                    user?.email?.takeLast(10)
                }"
            ),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
    }
    Text(
        text = "-${
            if (couponAmount > 0.0) formatNumber(couponAmount)
            else currency.amount
        } ${currency.symbol}",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = 32.dp),
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp
    )

    AnimatedVisibility(visible = onAddCoupon) {
        Row(
            Modifier
                .padding(16.dp)
                .background(CakkieBackground)
        ) {
            CakkieInputField(
                value = coupon,
                onValueChange = { coupon = it },
                placeholder = "Enter Coupon",
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CakkieButton(
                Modifier
                    .height(50.dp)
                    .padding(horizontal = 10.dp),
                text = stringResource(id = R.string.apply),
                enabled = coupon.text.isNotEmpty(),
                processing = processing
            ) {
                processing = true
                viewModel.addCoupon(
                    coupon.text,
                    currency.symbol,
                    currency.amount.replace(",", "").toDouble()
                ).addOnSuccessListener {
                    processing = false
                    couponAmount = it.payableAmount
                    onAddCoupon = false
                }.addOnFailureListener {
                    processing = false
                    Toaster(context, it, R.drawable.logo).show()
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }

    }
    Text(
        text = if (onAddCoupon) "Cancel"
        else if (couponAmount > 0.0) "Remove coupon"
        else "Add Coupon",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .clickable {
                if (couponAmount > 0.0) {
                    couponAmount = 0.0
                    coupon = TextFieldValue("")
                } else onAddCoupon = !onAddCoupon
            },
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = CakkieBrown,
        textAlign = TextAlign.End,
        textDecoration = TextDecoration.Underline,
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
        AnimatedVisibility(step == 2) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = if (timerRunning) timer.toString() + "s"
            else stringResource(id = R.string.resend_code),
                style = MaterialTheme.typography.titleMedium,
                color = CakkieBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        if (!timerRunning) {
                            timerRunning = true
                            viewModel
                                .resendOtp(email = user?.email ?: "")
                                .addOnSuccessListener {
                                    timerRunning = true
                                    Toaster(
                                        context = context,
                                        message = "Otp Sent",
                                        image = R.drawable.logo
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toaster(
                                        context = context,
                                        message = "Otp Resend Failed",
                                        image = R.drawable.logo
                                    ).show()
                                }
                        }
                    })

        }
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
                                    if (pin.text.length < 4)
                                        pin.copy(text = pin.text + (row + col).toString()) else pin

                                1 -> pinConfirm =
                                    if (pinConfirm.text.length < 4)
                                        pinConfirm.copy(text = pinConfirm.text + (row + col).toString())
                                    else pinConfirm

                                else -> otp =
                                    if (otp.text.length < 4)
                                        otp.copy(text = otp.text + (row + col).toString()) else otp
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
                                else if (pinConfirm.text.length < 4)
                                    pinConfirm.copy(text = pinConfirm.text + "0") else pinConfirm

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

    Spacer(modifier = Modifier.height(16.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.pay_solana),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(250.dp)
                .padding(horizontal = 32.dp)
                .clickable {
                    if (enabled) {
                        when (step) {
                            0 -> {
                                step = 1
                            }

                            1 -> {
                                if (user?.pin == null) {
                                    step = 2
                                    viewModel
                                        .resendOtp(email = user?.email ?: "")
                                        .addOnSuccessListener {
                                            timerRunning = true
                                            Toaster(
                                                context = context,
                                                message = "Otp Sent",
                                                image = R.drawable.logo
                                            ).show()
                                        }
                                        .addOnFailureListener {
                                            Toaster(
                                                context = context,
                                                message = "Otp Resend Failed",
                                                image = R.drawable.logo
                                            ).show()
                                        }
                                } else {
                                    processing = true
                                    viewModel
                                        .verifyPin(pinConfirm.text)
                                        .addOnSuccessListener {
                                            processing = false
                                            navigator.navigate(
                                                BrowserDestination(
                                                    "https://cakkie.com/solana-pay?desc=${"cakkie"}&userId=${user.id}&currencySymbol=${currency.symbol}&price=${
                                                        if (couponAmount > 0.0) formatNumber(
                                                            couponAmount
                                                        )
                                                        else currency.amount
                                                    }"
                                                )
                                            )
                                        }
                                        .addOnFailureListener {
                                            processing = false
                                            Toaster(context, it, R.drawable.logo).show()
                                        }
                                }
                            }

                            2 -> {
                                processing = true
                                viewModel
                                    .resetPin(pin.text, pinConfirm.text, otp.text)
                                    .addOnSuccessListener {
                                        processing = false
                                        Toaster(context, it.message, R.drawable.logo).show()
                                        step = 1
                                        viewModel.getProfile()
                                    }
                                    .addOnFailureListener {
                                        processing = false
                                        Toaster(context, it, R.drawable.logo).show()
                                    }
                            }
                        }
                    } else {
                        Toaster(context, "Pay attention to pin", R.drawable.logo).show()
                    }
                }
        )
    }

    Spacer(modifier = Modifier.height(26.dp))
    CakkieButton(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        text = stringResource(id = R.string.proceed),
        enabled = enabled,
        processing = processing
    ) {
        when (step) {
            0 -> {
                step = 1
            }

            1 -> {
                if (user?.pin == null) {
                    step = 2
                    viewModel
                        .resendOtp(email = user?.email ?: "")
                        .addOnSuccessListener {
                            timerRunning = true
                            Toaster(
                                context = context,
                                message = "Otp Sent",
                                image = R.drawable.logo
                            ).show()
                        }
                        .addOnFailureListener {
                            Toaster(
                                context = context,
                                message = "Otp Resend Failed",
                                image = R.drawable.logo
                            ).show()
                        }
                } else {
                    processing = true
                    viewModel.verifyPin(pinConfirm.text)
                        .addOnSuccessListener {
                            processing = false
                            onComplete.navigateBack(
                                currency.copy(
                                    pin = pinConfirm.text,
                                    symbol = currency.symbol,
                                    coupon = coupon.text
                                )
                            )
                        }.addOnFailureListener {
                            processing = false
                            Toaster(context, it, R.drawable.logo).show()
                        }
                }
            }

            2 -> {
                processing = true
                viewModel.resetPin(pin.text, pinConfirm.text, otp.text)
                    .addOnSuccessListener {
                        processing = false
                        Toaster(context, it.message, R.drawable.logo).show()
                        step = 1
                        viewModel.getProfile()
                    }
                    .addOnFailureListener {
                        processing = false
                        Toaster(context, it, R.drawable.logo).show()
                    }
            }
        }
    }


    Spacer(modifier = Modifier.height(17.dp))

    AnimatedVisibility(visible = onSelectCurrency) {
        Popup(
            onDismissRequest = { onSelectCurrency = false }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = CardDefaults.elevatedShape
                ) {
                    Column(
                        Modifier
                            .padding(16.dp)
                            .background(CakkieBackground)
                    ) {
                        currencies.forEach {
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(Modifier.clickable {
                                currency = it
                                onSelectCurrency = false
                            }) {
                                Text(
                                    text = "-${it.amount} ${it.symbol}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.Center),
                                    color = TextColorDark,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }

    }


}