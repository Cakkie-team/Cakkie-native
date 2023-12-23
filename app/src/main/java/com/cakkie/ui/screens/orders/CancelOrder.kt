package com.cakkie.ui.screens.orders

import androidx.compose.material3.Button
import com.cakkie.ui.theme.Error
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CancelButton
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun CancelOrder(navigator: DestinationsNavigator){
    val openDialog = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painterResource(id = R.drawable.arrow_back), contentDescription = "Go back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    },
            )
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = stringResource(id = R.string.ordered_from_cakkie),
                style = MaterialTheme.typography.labelMedium,

                )
            Text(
                text = " Jane Doe",
                style = MaterialTheme.typography.labelMedium,
                color = CakkieBrown
            )
        }
        Row {
            Text(
                text = "Posted 5 Minutes ago ",
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(alpha = 0.7f)
            )
            Text(
                text = "15 Minutes 30 seconds ",
                style = MaterialTheme.typography.bodySmall,
                color = TextColorDark.copy(alpha = 0.7f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            color = CakkieBrown,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "HI, I would like to klnfn  jwfjwkh jiefj;wi hfwiu hrif hukseh fuio " +
                "woyrory r yrororywer;iorryo   row;tyr8tw8o \n y8wyoiy/iy o ryhfo /yoryi r" +
                " wioyoi uw/wyf8uyhriof y rwry8roryuirtg.8o  fwry.riuyrukyr8ir.rufyr.grog8y/o",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(id = R.string.size),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "6 inches:",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(id = R.string.medium_sized_pan),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.proposed_price),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "NGN 20,000",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 35.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.shape),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.rounded_shape),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.location),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Lagos State",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 70.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Image(
                        painter = painterResource(
                            id = R.drawable.progress
                        ),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(id = R.string.pending_acceptance),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.icing),
                    style = MaterialTheme.typography.labelMedium,
                    color = CakkieBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "300 ",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
CancelButton(
    text = stringResource(id = R.string.cancel_order)
) {
    openDialog.value = true
        }
        if (openDialog.value)
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.cakkie_icon),
                                contentDescription = ""
                            )
                            Text(
                                text = stringResource(id = R.string.warning),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.are_you_sure_you_want_to_cancel_this_order),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                confirmButton =
                    {
                       Button(
                            onClick = { },
                           colors = ButtonDefaults.buttonColors(
                               contentColor = CakkieBrown,
                               containerColor = Color.Transparent
                           )
                               ){
                               Text(text = stringResource(id = R.string.yes,
                                   )
                               )
                           }
                    },
                dismissButton = {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = CakkieBrown,
                            containerColor = Color.Transparent
                        )
                    ){
                            Text(text = stringResource(id = R.string.no,
                                )
                            )
                        }
                    },
                containerColor = CakkieBackground,
                shape = RectangleShape,
                 modifier = Modifier.border(
                    width = 1.dp,
                    color = CakkieBrown,
                )
            )
    }
}


