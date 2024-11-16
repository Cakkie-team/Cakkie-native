package com.cakkie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.utill.Toaster

@Composable
fun SolanaPayBt(enabled: Boolean, onClick: () -> Unit) {
    val context = LocalContext.current
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
                        onClick()
                    } else {
                        Toaster(context, "Enter the required field", R.drawable.logo).show()
                    }
                }
        )
    }

}