package com.cakkie.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.networkModels.CurrencyRate
import com.cakkie.networkModels.PreferenceModel
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.screens.destinations.ConfirmPinDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieBrown002
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.utill.Toaster
import com.cakkie.utill.formatNumber
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit

@Destination
@Composable
fun Subscription(
    confirmPinResult: ResultRecipient<ConfirmPinDestination, CurrencyRate>,
    navigator: DestinationsNavigator
) {
    val viewModel: ShopViewModel = koinViewModel()
    val shop = viewModel.shop.observeAsState().value
    val preference = viewModel.preference.observeAsState(PreferenceModel()).value
    var subType by remember {
        mutableIntStateOf(0)
    }
    var processing by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var isTopUp by remember {
        mutableStateOf(false)
    }

    // Current date and time in ISO format
    val currentDateTime = LocalDateTime.now()

// Parse the premium expiration date
    val premiumExpiresAt =
        shop?.premiumExpiresAt?.takeIf { it.isNotEmpty() } ?: currentDateTime.format(
            DateTimeFormatter.ISO_DATE_TIME
        )

    val targetDateTime = try {
        LocalDateTime.parse(premiumExpiresAt, DateTimeFormatter.ISO_DATE_TIME)
    } catch (e: Exception) {
        // Handle any parsing exceptions and fallback to current date time or a default value
        currentDateTime
    }

// Calculate the number of days left
    val daysLeft = ChronoUnit.DAYS.between(currentDateTime, targetDateTime).toInt()


    confirmPinResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                processing = true
                val allowedSymbols = setOf("NGN", "ICING")

                if (result.value.symbol.trim() !in allowedSymbols) {
                    Toaster(
                        context,
                        "Sorry ${result.value.symbol} is not allowed for subscription",
                        R.drawable.logo
                    ).show()
                    processing = false
                } else {
                    viewModel.subscribeShop(
                        result.value.pin,
                        result.value.coupon.trim(),
                        result.value.symbol.trim(),
                        if (subType == 0) 12
                        else 1,
                    ).addOnSuccessListener {
                        processing = false
                        viewModel.getMyShop()
                    }.addOnFailureListener {
                        processing = false
                        Toaster(context, it, R.drawable.logo).show()
                    }

                }
            }
        }
    }

    Column {
        if ((shop?.isPremium == false || isTopUp).not()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navigator.popBackStack()
                        }
                        .size(24.dp)
                )

                Text(
                    text = stringResource(id = R.string.subscription),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 16.sp
                )

            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sub),
                    contentDescription = "Subscription",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(id = R.drawable.sub1),
                    contentDescription = "Subscription",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.5f),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Column(Modifier.verticalScroll(rememberScrollState())) {
            if (shop?.isPremium == false || isTopUp) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { subType = 0 }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                ) {
                    Row(
                        Modifier
                            .padding(start = 10.dp, end = 18.dp, top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = subType == 0,
                                onCheckedChange = {
                                    subType = 0
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = CakkieBrown,
                                    uncheckedColor = CakkieBrown002
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(id = R.string.annually),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "NGN${formatNumber(preference.premiumYearlyFee)}/month",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { subType = 1 }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                ) {
                    Row(
                        Modifier
                            .padding(start = 10.dp, end = 18.dp, top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = subType == 1,
                                onCheckedChange = {
                                    subType = 1
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = CakkieBrown,
                                    uncheckedColor = CakkieBrown002
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(id = R.string.monthly),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "NGN${formatNumber(preference.premiumMonthlyFee)}/month",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            } else {
                //text for premium users
                Text(
                    text = "You are already a premium user",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = CakkieBrown,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .background(CakkieLightBrown, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$daysLeft days left",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }

                //your subscription details
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your subscription has the following benefits",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            listOf(
                "Premium membership badge",
                "Unlimited access to all features",
                "High visibility priority",
                "24/7 customer support",
                "Extended storage",
            ).forEach {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = CakkieBrown,
                            uncheckedColor = CakkieBrown002
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            CakkieButton(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(id = if ((shop?.isPremium == false || isTopUp).not()) R.string.top_up else R.string.subscribe),
                processing = processing,
                enabled = preference.premiumMonthlyFee > 0,
            ) {
                if ((shop?.isPremium == false || isTopUp).not()) {
                    isTopUp = true
                } else {
                    navigator.navigate(
                        ConfirmPinDestination(
                            CurrencyRate(
                                amount = if (subType == 0) formatNumber(
                                    preference.premiumYearlyFee.times(
                                        12
                                    )
                                )
                                else formatNumber(preference.premiumMonthlyFee),
                                symbol = "NGN"
                            )
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}