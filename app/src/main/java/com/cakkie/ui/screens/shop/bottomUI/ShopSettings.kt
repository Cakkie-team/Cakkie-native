package com.cakkie.ui.screens.shop.bottomUI

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.screens.destinations.EditShopDestination
import com.cakkie.ui.screens.destinations.SubscriptionDestination
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet


@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ShopSettings(navigator: DestinationsNavigator) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .clip(RoundedCornerShape(50))
//                .height(8.dp)
                .width(72.dp)
                .align(Alignment.CenterHorizontally),
            color = CakkieBrown,
            thickness = 8.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.clickable {
                navigator.navigate(EditShopDestination)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "share",
                modifier = Modifier
                    .size(24.dp),
                tint = CakkieBrown
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.edit_shop),
                style = MaterialTheme.typography.labelLarge,
                color = CakkieBrown,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.clickable { navigator.navigate(SubscriptionDestination) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.orders),
                contentDescription = "flag",
                modifier = Modifier
                    .size(24.dp),
                tint = CakkieBrown
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.manage_subscription),
                style = MaterialTheme.typography.labelLarge,
                color = CakkieBrown,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.height(26.dp))

    }

}