package com.cakkie.ui.screens.profile.bottomUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ChangeProfileItem(
    navigator: DestinationsNavigator
) {
    var processing by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "approved",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(20.dp)
                    .padding(horizontal = 5.dp)


            )
            Text(
                text = stringResource(id = R.string.make_changes_saved),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        Text(
            text = stringResource(id = R.string.save_changes_message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(35.dp))

        CakkieButton(
            Modifier
                .height(50.dp)
                .width(328.dp),
            processing = processing,
            text = stringResource(id = R.string.sure)
        ) {
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

}
