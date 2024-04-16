package com.cakkie.ui.screens.profile

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.ChangeProfileItemDestination
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.getCurrentLocation
import com.cakkie.utill.locationModels.LocationResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun ChangeProfile(
    onConfirm: ResultRecipient<ChangeProfileItemDestination, Boolean>,
    navigator: DestinationsNavigator
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val user = viewModel.user.observeAsState().value
    var name by remember {
        mutableStateOf(TextFieldValue(user?.name ?: ""))
    }
    var phoneNumber by remember {
        mutableStateOf(TextFieldValue(user?.phoneNumber ?: ""))
    }

    var address by remember {
        mutableStateOf(TextFieldValue(user?.address ?: ""))
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var location by remember {
        mutableStateOf<LocationResult?>(null)
    }

    val context = LocalContext.current
    val activity = context as Activity
    var processing by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var uploding by remember {
        mutableStateOf(false)
    }
    var uploadMessage by remember {
        mutableStateOf("Upload a business logo")
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )
    var fileUrl by remember {
        mutableStateOf("")
    }
    val currentLocation = activity.getCurrentLocation()

    //set initial values
    LaunchedEffect(user) {
        if (user != null) {
            name = TextFieldValue(user.name)
            phoneNumber = user.phoneNumber?.let { TextFieldValue(it) } ?: TextFieldValue("")
            address = TextFieldValue(user.address)
            fileUrl = user.profileImage
        }
    }

    onConfirm.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                processing = true
                if (imageUri != null && user != null) {
                    uploding = true
                    val file = context.createTmpFileFromUri(
                        uri = imageUri!!,
                        fileName = "shopLogo"
                    )!!
                    viewModel.uploadImage(
                        image = file,
                        path = "profile-images",
                        fileName = file.name + ".png"
                    ).addOnSuccessListener { resp ->
                        fileUrl = Endpoints.FILE_URL("profile-images/" + file.name + ".png")
                        Timber.d(resp)
                        uploadMessage = "profile image uploaded"
                        uploding = false
                        file.delete()
                        viewModel.updateProfile(
                            firstName = name.text.split(" ").first().ifEmpty { user.firstName },
                            lastName = name.text.split(" ").last().ifEmpty { user.lastName },
                            phone = phoneNumber.text,
                            address = address.text,
                            imageUrl = fileUrl,
                            location = location!!
                        ).addOnSuccessListener { user ->
                            viewModel.getProfile()
                            processing = false
                            navigator.popBackStack()
                        }.addOnFailureListener {
                            processing = false
                            uploadMessage = "Failed to update profile, try again"
                        }
                    }.addOnFailureListener { exception ->
                        Timber.d(exception)
                        uploding = false
                        processing = false
                        uploadMessage = "Failed to upload profile image, try again"
                        file.delete()
                    }
                } else if (user != null) {
                    processing = true
                    viewModel.updateProfile(
                        firstName = name.text.split(" ").first().ifEmpty { user.firstName },
                        lastName = name.text.split(" ").last().ifEmpty { user.lastName },
                        phone = phoneNumber.text,
                        address = address.text,
                        imageUrl = fileUrl,
                        location = location!!
                    ).addOnSuccessListener { user ->
                        viewModel.getProfile()
                        processing = false
                        navigator.popBackStack()
                    }.addOnFailureListener {
                        processing = false
                        uploadMessage = "Failed to update profile, try again"
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = uploadMessage) {
        if (uploadMessage.isNotEmpty()) {
            //show toast
            Toaster(
                context = context,
                message = uploadMessage,
                image = R.drawable.logo
            ).show()
            uploadMessage = ""
        }
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",

                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navigator.popBackStack()
                    }
                    .size(24.dp)
            )

            Text(
                text = stringResource(id = R.string.edit_profile),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                GlideImage(
                    model = imageUri ?: user?.profileImage?.replace("http", "https")
                    ?: "https://source.unsplash.com/100x150/?cake?logo",
                    contentDescription = "cake logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            galleryLauncher.launch("image/*")
                        }
                )
                if (uploding) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp),
                        strokeWidth = 2.dp,
                        color = CakkieBrown,
                    )
                }
            }
            Text(
                text = user?.name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
//                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(9.dp))

            CakkieButton(
                text = stringResource(id = R.string.change_profile_picture),
            ) {
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CakkieInputField(
                value = name,
                onValueChange = {
                    isError = true
                    name = it
                },
                showEditIcon = true,
                placeholder = "Jennifer Victor",
                keyboardType = KeyboardType.Text,
            )
            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.End),
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(20.dp))

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
            Text(
                text = stringResource(id = R.string.address_City_State),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.End),
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(20.dp))

            CakkieInputField(
                value = phoneNumber,
                onValueChange = {
                    isError = true
                    phoneNumber = it
                },
                showEditIcon = true,
                placeholder = "08001010101",
                keyboardType = KeyboardType.Phone,
            )
            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.End),
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                color = CakkieBrown
            )
            Spacer(modifier = Modifier.height(50.dp))

            CakkieButton(
                Modifier
                    .width(328.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(id = R.string.save_changes)
            ) {
                navigator.navigate(ChangeProfileItemDestination)

            }
            Spacer(modifier = Modifier.height(17.dp))
        }

    }
}
