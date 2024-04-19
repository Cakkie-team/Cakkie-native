package com.cakkie.ui.screens.shop

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
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
    var description by remember {
        mutableStateOf(TextFieldValue(shop?.description ?: ""))
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
        mutableStateOf("")
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
            description = TextFieldValue(shop.description)
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
                        viewModel.updateShop(
                            name = name.text,
                            description = description.text,
                            address = address.text,
                            imageUrl = fileUrl,
                            location = location!!
                        ).addOnSuccessListener { shop ->
                            viewModel.getProfile()
                            processing = false
                            navigator.popBackStack()
                        }.addOnFailureListener {
                            processing = false
                            uploadMessage = "Failed to update shop, try again"
                        }
                    }.addOnFailureListener { exception ->
                        Timber.d(exception)
                        uploding = false
                        processing = false
                        uploadMessage = "Failed to upload shop image, try again"
                        file.delete()
                    }
                } else if (shop != null) {
                    processing = true
                    viewModel.updateShop(
                        name = name.text,
                        description = description.text,
                        address = address.text,
                        imageUrl = fileUrl,
                        location = location!!
                    ).addOnSuccessListener { Shop ->
                        processing = false
                        navigator.popBackStack()
                    }.addOnFailureListener {
                        processing = false
                        uploadMessage = "Failed to update shop, try again"
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
                Box(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 20.dp), contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageUri
                            ?: fileUrl.ifEmpty { "https://source.unsplash.com/100x150/?cake?logo" },
                        contentDescription = "shop logo",
                        contentScale = ContentScale.Crop,
                        onState = {
                            //update isLoaded
                            loading = it is AsyncImagePainter.State.Loading
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                color = CakkieBackground,
                                shape = CircleShape
                            )
                            .placeholder(
                                visible = loading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = CakkieBrown.copy(0.8f)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(94.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(0.3f))
                            .clickable {
                                if (!uploding) galleryLauncher.launch("image/*")
                            }, contentAlignment = Alignment.Center
                    ) {
                        if (uploding) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(30.dp),
                                strokeWidth = 2.dp,
                                color = CakkieBackground,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ph_plus),
                                contentDescription = "upload image",
                                modifier = Modifier.size(30.dp),
                                tint = CakkieBackground
                            )
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .clickable {
                            navigator.navigate(ChangeProfileItemDestination)
                        }
                        .border(
                            width = 1.dp,
                            color = CakkieBrown,
                            shape = RoundedCornerShape(20)
                        )
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End,
                        color = CakkieBrown
                    )
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
                value = description,
                onValueChange = {
                    isError = true
                    description = it
                },
                showEditIcon = true,
                placeholder = stringResource(id = R.string.about_business),
                keyboardType = KeyboardType.Text,
                singleLine = false,
                modifier = Modifier.height(120.dp)
            )
            Text(
                text = stringResource(id = R.string.description),
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
