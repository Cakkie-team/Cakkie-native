package com.cakkie.ui.screens.wallet.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.CompletedDestination
import com.cakkie.ui.screens.destinations.CompletedOrderDestination
import com.cakkie.ui.screens.destinations.CompletedOrdersDestination
import com.cakkie.ui.screens.destinations.DeclinedDestination
import com.cakkie.ui.screens.destinations.InProgressDestination
import com.cakkie.ui.screens.destinations.OngoingOrderDestination
import com.cakkie.ui.screens.destinations.OrdersDestination
import com.cakkie.ui.screens.destinations.PendingDestination
import com.cakkie.ui.screens.destinations.PendingOrdersDestination
import com.cakkie.ui.screens.orders.OrderState
import com.cakkie.ui.screens.orders.componentscrrens.InProgress
import com.cakkie.ui.screens.orders.componentscrrens.PendingOrders
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletFilter (){
//fun WalletFilter (navigator: DestinationsNavigator){
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(120.dp)
            .height(187.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 160.dp, height = 94.dp)
                .background(CakkieBackground)
                .clip(RoundedCornerShape(8))
        ) {
            OutlinedTextField(
                value = "Filters",
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .padding(start = 20.dp)
                    .size(width = 84.dp, height = 28.dp)
                    .background(CakkieBackground),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CakkieBrown,
                )
            )
            ExposedDropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.all),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.clickable {
//                            navigator.navigate((OrdersDestination()))
                        }
                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.proposal_fee),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {
//                            navigator.navigate(OngoingOrderDestination())
                        }

                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.icing_purchase),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {
//                            navigator.navigate(PendingOrdersDestination())
                        }
                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.withdrawal),
                            style = MaterialTheme.typography.labelSmall,
                            modifier =  Modifier.clickable {
//                                navigator.navigate(CompletedOrdersDestination())
                            }
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.deposit),
                            style = MaterialTheme.typography.labelSmall,
                            modifier =  Modifier.clickable {
//                                navigator.navigate(CompletedOrdersDestination())
                            }
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.orders),
                            style = MaterialTheme.typography.labelSmall,
                            modifier =  Modifier.clickable {
//                                navigator.navigate(CompletedOrdersDestination())
                            }
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }
}