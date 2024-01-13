package com.cakkie.ui.screens.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WalletHistory1 (){
//    val calendar = Calendar.getInstance().time
//    val dateFormat = DateFormat.getDateInstance(DateFormat.).format()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
Box(modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp)
){
    Image(painter = painterResource(id = R.drawable.arrow_back), contentDescription = "")
    Text(
        text = stringResource(id = R.string.wallet),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.align(Alignment.Center)
    )
}
        Spacer(modifier = Modifier.height(80.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            CakkieButton(
                text = stringResource(id = R.string.withdraw),
            ) {
            }
            CakkieButton(
                text = stringResource(id = R.string.fund_wallet),
            ) {
            }
            CakkieButton(
                text = stringResource(id = R.string.icing_purchase),
            ) {
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = R.string.transaction_history),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(id = R.string.see_all),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(id = R.string.today),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .height(80.dp)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(painter = painterResource(id = R.drawable.badge), contentDescription = "")
                Column (
                    modifier = Modifier.padding(start = 5.dp)
                ){
                    Row {
                        Text(text = "NGN5,000 ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown,
                        )
                        Text(text = stringResource(id = R.string.withdrawal_successful),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(text = "20 May by 10:50 AM ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColorDark.copy(0.7f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = stringResource(id = R.string.view),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .height(80.dp)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(painter = painterResource(id = R.drawable.badge), contentDescription = "")
                Column (
                    modifier = Modifier.padding(start = 5.dp)
                ){
                    Row {
                        Text(text = "100 ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CakkieBrown,
                        )
                        Text(text = stringResource(id = R.string.icing_purchase_successful),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(text = "20 May by 10:50 AM ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColorDark.copy(0.7f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = stringResource(id = R.string.view),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CakkieBrown)
            }
        }
    }
}
