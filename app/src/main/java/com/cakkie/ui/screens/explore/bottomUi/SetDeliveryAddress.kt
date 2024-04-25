package com.cakkie.ui.screens.explore.bottomUi


import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.data.db.models.User
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.profile.ProfileViewModel
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Toaster
import com.cakkie.utill.getCurrentLocation
import com.cakkie.utill.locationModels.LocationResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun SetDeliveryAddress(onComplete: ResultBackNavigator<User>) {
    val viewModel: ProfileViewModel = koinViewModel()
    var address by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var location by remember {
        mutableStateOf<LocationResult?>(null)
    }

    val context = LocalContext.current
    val activity = context as Activity
    var processing by remember {
        mutableStateOf(false)
    }

    val currentLocation = activity.getCurrentLocation()

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
        Text(
            text = stringResource(id = R.string.set_delivery_address),
            style = MaterialTheme.typography.labelLarge,
            color = CakkieBrown,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
        CakkieInputField(
            value = address,
            onValueChange = { address = it },
            placeholder = stringResource(id = R.string.address_City_State),
            keyboardType = KeyboardType.Text,
            isAddress = true,
            isEditable = false,
            location = currentLocation,
            onLocationClick = {
                location = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CakkieButton(text = stringResource(id = R.string.save)) {
            processing = true
            viewModel.updateAddress(
                address = address.text,
                location = location!!
            ).addOnSuccessListener { user ->
                viewModel.getProfile()
                processing = false
                onComplete.navigateBack(user)
            }.addOnFailureListener {
                processing = false
                Toaster(
                    context,
                    it.message ?: "Sorry unable to update address",
                    R.drawable.logo
                ).show()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}