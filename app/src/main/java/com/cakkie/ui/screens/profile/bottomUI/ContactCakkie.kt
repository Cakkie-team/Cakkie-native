package com.cakkie.ui.screens.profile.bottomUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpItems(navigator: DestinationsNavigator) {
    var processing by remember {
        mutableStateOf(false)
    }
      Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .padding(horizontal = 32.dp)
    ) {

        Text(
            text = stringResource(id = R.string.conatct_cakkie),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Text(
            text = stringResource(id = R.string.contact_cakkie_message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(19.dp))

        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
            text = stringResource(id = R.string.call_us)
        ) {
        }
        Spacer(modifier = Modifier.height(10.dp))


        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
            text = stringResource(id = R.string.send_us_an_email)
        ) {
        }
        Spacer(modifier = Modifier.height(10.dp))


        CakkieButton(
            Modifier.height(50.dp),
            processing = processing,
            text = stringResource(id = R.string.send_us_an_instagram_message)
        ) {
        }
        Spacer(modifier = Modifier.height(38.dp))

    }
}