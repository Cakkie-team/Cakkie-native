package com.cakkie.ui.screens.shop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.screens.destinations.CreateShopDestination
import com.cakkie.ui.screens.destinations.ShopDestination
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.utill.Endpoints
import com.cakkie.utill.Toaster
import com.cakkie.utill.createTmpFileFromUri
import com.cakkie.utill.getCurrentLocation
import com.cakkie.utill.locationModels.LocationResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun CreateShop(navigator: DestinationsNavigator) {
    val viewModel: ShopViewModel = koinViewModel()
    val context = LocalContext.current
    var name by remember {
        mutableStateOf(TextFieldValue(""))
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

    LaunchedEffect(key1 = imageUri) {
        if (imageUri != null) {
            uploding = true
            val file = context.createTmpFileFromUri(
                uri = imageUri!!,
                fileName = "shopLogo"
            )!!
            viewModel.uploadImage(
                image = file,
                path = "shop-logos",
                fileName = file.name + ".png"
            ).addOnSuccessListener { resp ->
                fileUrl = Endpoints.FILE_URL("shop-logos/" + file.name + ".png")
                Timber.d(resp)
                uploadMessage = "Logo uploaded"
                uploding = false
                file.delete()
            }.addOnFailureListener { exception ->
                Timber.d(exception)
                uploding = false
                uploadMessage = "Failed to upload logo, try again"
                file.delete()
            }
        }
    }

    var address by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var description by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isChecked by remember {
        mutableStateOf(false)
    }
    var processing by remember {
        mutableStateOf(false)
    }
    var location by remember {
        mutableStateOf<LocationResult?>(null)
    }
    val activity = context as Activity
    val currentLocation = activity.getCurrentLocation()

    val canProceed = name.text.isNotBlank() &&
            address.text.isNotBlank() &&
            description.text.isNotBlank() &&
            isChecked && fileUrl.isNotBlank() && location != null

    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.cakkie_logo
                ),
                modifier = Modifier
                    .size(27.dp),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = R.string.shop),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = CakkieBrown,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(80.dp))
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            androidx.compose.material3.Text(
                text = stringResource(id = R.string.hello_there),
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.fill_out_this_form_to_get_your_shop),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(28.dp))

            Box(contentAlignment = Alignment.Center) {
                GlideImage(
                    model = imageUri ?: "https://source.unsplash.com/100x150/?cake?logo",
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uploadMessage,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = CakkieBrown,
            )

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = name,
                onValueChange = { name = it },
                placeholder = stringResource(id = R.string.business_name),
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(16.dp))
            CakkieInputField(
                value = description,
                onValueChange = { description = it },
                placeholder = stringResource(id = R.string.about_business),
                keyboardType = KeyboardType.Text,
                singleLine = false,
                modifier = Modifier.height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
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

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = CakkieBrown,
                        uncheckedColor = CakkieBrown,
                        checkmarkColor = CakkieBackground
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.i_agree_to),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.terms_of_Service),
                        color = CakkieBrown,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.cakkie.com/terms-and-conditions")
                            )
                            context.startActivity(intent)
                        },
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.and),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.privacy_Policy),
                        color = CakkieBrown,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.cakkie.com/privacy-policy")
                            )
                            context.startActivity(intent)
                        },
                        fontSize = 12.sp
                    )
                }


            }
            Spacer(modifier = Modifier.height(50.dp))

            CakkieButton(
                Modifier.fillMaxWidth(),
                processing = processing,
                enabled = canProceed,
                text = stringResource(id = R.string.create_shop)
            ) {

                // check if the email is valid
                processing = true
                viewModel.createShop(
                    name = name.text,
                    address = address.text,
                    imageUrl = fileUrl,
                    location = location!!,
                    description = description.text
                ).addOnSuccessListener { resp ->
                    Timber.d(resp.toString())
                    viewModel.getProfile().addOnSuccessListener {
                        processing = false
                        //navigate to shop screen
                        navigator.navigate(ShopDestination) {
                            launchSingleTop = true
                            popUpTo(CreateShopDestination) {
                                inclusive = true
                            }
                        }
                    }
                        .addOnFailureListener { exception ->
                            //show toast
                            Toaster(
                                context = context,
                                message = exception.localizedMessage ?: "Failed to get user",
                                image = R.drawable.logo
                            ).show()
                            processing = false
                            Timber.d(exception)
                            navigator.navigate(ShopDestination) {
                                launchSingleTop = true
                                popUpTo(CreateShopDestination) {
                                    inclusive = true
                                }
                            }
                        }
                }.addOnFailureListener { exception ->
                    //show toast
                    Toaster(
                        context = context,
                        message = exception,
                        image = R.drawable.logo
                    ).show()
                    processing = false
                    Timber.d(exception)
                }

            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}