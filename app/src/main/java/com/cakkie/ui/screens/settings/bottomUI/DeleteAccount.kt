package com.cakkie.ui.screens.settings.bottomUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.settings.SettingsViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun DeleteAccount(onComplete: ResultBackNavigator<Boolean>) {
    val viewModel: SettingsViewModel = koinViewModel()
    var reason by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val context = LocalContext.current
    var processing by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(80.dp)
                .height(8.dp)
                .clip(CircleShape)
                .background(CakkieBrown)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.delete_account),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Text(
            text = stringResource(id = R.string.delete_account_message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Text(
            text = stringResource(id = R.string.if_you_delete_account),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.reason_for_deletion),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(4.dp))
        CakkieInputField(
            value = reason,
            onValueChange = { reason = it },
            singleLine = false,
            placeholder = "Type Something",
            keyboardType = KeyboardType.Text,
            modifier = Modifier
                .size(height = 129.dp, width = 330.dp)
                .clip(RoundedCornerShape(3.dp))
        )

    }
    Spacer(modifier = Modifier.height(15.dp))

    CakkieButton(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        text = stringResource(id = R.string.delete_account),
        processing = processing
    ) {
        processing = true
        viewModel.deleteAccount(reason.text)
            .addOnSuccessListener {
                processing = false
                Toaster(
                    context = context,
                    message = "Account Deleted Successfully!",
                    image = R.drawable.logo
                ).show()
                onComplete.navigateBack(result = true)
            }
            .addOnFailureListener {
                processing = false
                Toaster(
                    context = context,
                    message = "Failed to delete account, please contact support!",
                    image = R.drawable.logo
                ).show()
            }
    }
    Spacer(modifier = Modifier.height(17.dp))

}
