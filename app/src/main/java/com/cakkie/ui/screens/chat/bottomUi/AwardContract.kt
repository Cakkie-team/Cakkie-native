package com.cakkie.ui.screens.chat.bottomUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun AwardContract(
    name: String,
    onComplete: ResultBackNavigator<String>,
) {
    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(80.dp)
                .height(5.dp)
                .clip(CircleShape)
                .background(CakkieBrown)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

//            Image(
//                painter = painterResource(id = R.drawable.edit),
//                contentDescription = "approved",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .size(20.dp)
//                    .padding(horizontal = 5.dp)
//
//
//            )
            Text(
                text = stringResource(id = R.string.contract_confirmation, name),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CakkieButton(
            Modifier
                .fillMaxWidth(),
            processing = processing,
            text = stringResource(id = R.string.sure)
        ) {
            onComplete.navigateBack(result = "it")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}