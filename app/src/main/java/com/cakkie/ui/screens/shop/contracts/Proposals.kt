package com.cakkie.ui.screens.shop.contracts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorInactive

@Composable
fun Proposals() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.nothing_to_show),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = CakkieBrown,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.get_started_now_by_checking_out_our_available_jobs_to_write),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextColorInactive,
                textAlign = TextAlign.Center
            )

        }

        CakkieButton(
            text = stringResource(id = R.string.check_jobs),
            modifier = Modifier
//                .fillMaxWidth(0.8f)
                .offset(y = (-30).dp)
                .align(Alignment.BottomCenter)
        ) {


        }
    }
}