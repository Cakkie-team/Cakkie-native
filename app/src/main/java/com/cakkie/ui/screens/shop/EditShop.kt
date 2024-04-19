package com.cakkie.ui.screens.shop

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.ChangeProfileItemDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.getCurrentLocation
import com.cakkie.utill.locationModels.LocationResult
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun EditShop(
    onConfirm: ResultRecipient<ChangeProfileItemDestination, Boolean>,
    navigator: DestinationsNavigator
) {
    val viewModel: ShopViewModel = koinViewModel()
    val shop = viewModel.shop.observeAsState().value
    var name by remember {
        mutableStateOf(TextFieldValue(shop?.name ?: ""))
    }
    var phoneNumber by remember {
        mutableStateOf(TextFieldValue(shop?.state ?: ""))
    }

    var address by remember {
        mutableStateOf(TextFieldValue(shop?.address ?: ""))
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
    LaunchedEffect(shop) {
        if (shop != null) {
            name = TextFieldValue(shop.name)
            address = TextFieldValue(shop.address)
            fileUrl = shop.image.replace(Regex("\\bhttp://"), "https://")
        }
    }

    onConfirm.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                processing = true
                if (imageUri != null && shop != null) {
                    uploding = true
                    val file = context.createTmpFileFromUri(
                        uri = imageUri!!,
                        fileName = "shopLogo"
                    )!!
                    viewModel.uploadImage(
                        image = file,
                        path = "shop-logos",
                        fileName = shop.name.replace(" ", "") + ".png"
                    ).addOnSuccessListener { resp ->
                        fileUrl =
                            Endpoints.FILE_URL("shop-logos/" + shop.name.replace(" ", "") + ".png")
                        Timber.d(resp)
                        uploadMessage = "profile image uploaded"
                        uploding = false
                        imageUri = null
                        file.delete()
//                        viewModel.updateProfile(
//                            firstName = name.text.split(" ").first().ifEmpty { shop.firstName },
//                            lastName = name.text.split(" ").last().ifEmpty { shop.lastName },
//                            phone = phoneNumber.text,
//                            address = address.text,
//                            imageUrl = fileUrl,
//                            location = location!!
//                        ).addOnSuccessListener { user ->
//                            viewModel.getProfile()
//                            processing = false
//                            navigator.popBackStack()
//                        }.addOnFailureListener {
//                            processing = false
//                            uploadMessage = "Failed to update profile, try again"
//                        }
                    }.addOnFailureListener { exception ->
                        Timber.d(exception)
                        uploding = false
                        processing = false
                        uploadMessage = "Failed to upload profile image, try again"
                        file.delete()
                    }
                } else if (shop != null) {
                    processing = true
//                    viewModel.updateProfile(
//                        firstName = name.text.split(" ").first().ifEmpty { shop.firstName },
//                        lastName = name.text.split(" ").last().ifEmpty { shop.lastName },
//                        phone = phoneNumber.text,
//                        address = address.text,
//                        imageUrl = fileUrl,
//                        location = location!!
//                    ).addOnSuccessListener { user ->
//                        viewModel.getProfile()
//                        processing = false
//                        navigator.popBackStack()
//                    }.addOnFailureListener {
//                        processing = false
//                        uploadMessage = "Failed to update profile, try again"
//                    }
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
                text = stringResource(id = R.string.edit_shop),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 16.sp
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
                    .height(150.dp),
                contentAlignment = Alignment.TopStart
            ) {
                var isLoading by remember {
                    mutableStateOf(true)
                }
                AsyncImage(
                    model = imageUri
                        ?: fileUrl.ifEmpty { "https://source.unsplash.com/100x150/?cake?logo" },
                    contentDescription = "cover",
                    contentScale = ContentScale.Crop,
                    onState = {
                        //update isLoaded
                        isLoading = it is AsyncImagePainter.State.Loading
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                        .height(100.dp)
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = CakkieBrown.copy(0.8f)
                        )
                )
                var loading by remember {
                    mutableStateOf(true)
                }
                AsyncImage(
                    model = imageUri
                        ?: fileUrl.ifEmpty { "https://source.unsplash.com/100x150/?cake?logo" },
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    onState = {
                        //update isLoaded
                        loading = it is AsyncImagePainter.State.Loading
                    },
                    modifier = Modifier
                        .padding(top = 50.dp, start = 20.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(100))
                        .border(
                            width = 3.dp,
                            color = CakkieBackground,
                            shape = RoundedCornerShape(100)
                        )
                        .placeholder(
                            visible = loading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = CakkieBrown.copy(0.8f)
                        )
                )
                if (!uploding) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 80.dp, start = 40.dp)
                            .size(30.dp),
                        strokeWidth = 2.dp,
                        color = CakkieBrown,
                    )
                } else {
                    Box(modifier = Modifier.clickable {
                        galleryLauncher.launch("image/*")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ph_plus),
                            contentDescription = "upload image",
                            modifier = Modifier.size(30.dp),
                            tint = CakkieBackground
                        )
                    }
                }

                CakkieButton(
                    Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .align(Alignment.BottomEnd),
                    text = stringResource(id = R.string.save),
                    processing = processing
                ) {
                    navigator.navigate(ChangeProfileItemDestination)

                }
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
        }

    }
}
