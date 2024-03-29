package com.cakkie.ui.screens.wallet

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.screens.wallet.components.WalletFilter
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.Error
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WalletHistory (){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            contentAlignment = Alignment.CenterStart
        ){
            Image(painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "", modifier = Modifier.clickable {  })
            Text(text = stringResource(id = R.string.transaction_history),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
                )
        }
        Spacer(modifier = Modifier.height(20.dp))
      WalletFilter()
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakicon),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = CakkieBackground,
                        shape = RoundedCornerShape(50)
                    )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.icing_purchase),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "100 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.withdrawal),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN10,000.00",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deposit),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN1,000.00",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakicon),
                contentDescription = "",
                modifier = Modifier.background(color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.icing_purchase),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "100 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.failed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Error
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deposit),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN12,000.00 ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.failed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Error
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cake_order),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "500 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.cakkie_icon),
                contentDescription = "",
                modifier = Modifier.background(CakkieBackground, RoundedCornerShape(50))
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.proposal_fee),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "50 Icing",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(78.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                modifier = Modifier.background(
                    color = CakkieBackground,
                    shape = RoundedCornerShape(50)
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.deposit),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "20 May, 10:50 am",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColorDark.copy(0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "NGN1,000.00",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold

                )
                Text(
                    text = stringResource(id = R.string.successful),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            }
        }
    }
}