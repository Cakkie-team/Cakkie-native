package com.cakkie.ui.components

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import com.cakkie.utill.getCurrentAddress
import com.cakkie.utill.getNearbyAddress
import com.cakkie.utill.getPlaceDetails
import com.cakkie.utill.locationModels.LocationResult
import com.cakkie.utill.searchAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CakkieInputField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    isAddress: Boolean = false,
    onLocationClick: (LocationResult) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    isEditable: Boolean = true,
    showEditIcon: Boolean = false,
    location: Location? = null,
    singleLine: Boolean = true
) {
    val scope = rememberCoroutineScope()
    var visible by remember {
        mutableStateOf(keyboardType != KeyboardType.Password)
    }
    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isEdit by remember {
        mutableStateOf(showEditIcon)
    }
    var showSearch by remember {
        mutableStateOf(false)
    }
    var addressList by remember {
        mutableStateOf(
            listOf<LocationResult>()
        )
    }

    LaunchedEffect(key1 = searchQuery, key2 = location) {
        if (isAddress && searchQuery.text.isEmpty()) {
            addressList = getNearbyAddress(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0
            )
        }
        if (searchQuery.text.isNotEmpty()) {
            addressList = searchAddress(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0,
                searchQuery.text
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (location != null && isAddress) {
//                                Timber.d("address is: "+context.getAddressFromLocation(location))
            scope.launch(Dispatchers.IO) {
                val address = getCurrentAddress(
                    location.latitude, location.longitude
                )
                onValueChange.invoke(
                    TextFieldValue(
                        address?.formattedAddress ?: ""
                    )
                )

                if (address != null) {
                    onLocationClick.invoke(address)
                }
            }
        }
    }
    Column {
        OutlinedTextField(
            isError = isError,
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextColorDark.copy(alpha = 0.5f),

                    )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = singleLine,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Transparent,
                focusedBorderColor = CakkieBrown,
                unfocusedBorderColor = CakkieBrown.copy(alpha = 0.5f),
                disabledBorderColor = Transparent,
                cursorColor = CakkieBrown,
                trailingIconColor = CakkieBrown,
                disabledTrailingIconColor = CakkieBrown.copy(alpha = 0.5f),
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick.invoke()
                                if (isAddress) {
                                    showSearch = !showSearch
                                }
                            }
                        }
                    }
                },
            modifier = modifier
                .fillMaxWidth(),
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    if (visible) {
                        Image(
                            painter = painterResource(id = R.drawable.eye_open),
                            contentDescription = "eye closed",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    visible = !visible
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.eye_closed),
                            contentDescription = "eye closed",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    visible = !visible
                                }
                        )
                    }
                }
                if (isEdit) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "edit",
                        modifier = Modifier.size(16.dp)

                    )
                } else {
                    VisualTransformation.None

                }
                if (isAddress) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "eye closed",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (location != null) {
//                                Timber.d("address is: "+context.getAddressFromLocation(location))
                                    scope.launch(Dispatchers.IO) {
                                        val address = getCurrentAddress(
                                            location.latitude, location.longitude
                                        )
                                        onValueChange.invoke(
                                            TextFieldValue(
                                                address?.formattedAddress ?: ""
                                            )
                                        )

                                        if (address != null) {
                                            onLocationClick.invoke(address)
                                        }
                                    }
                                }
                            }
                    )
                }
            },
            leadingIcon = leadingIcon,
            visualTransformation = if (!visible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            readOnly = !isEditable
        )
        DropdownMenu(
            expanded = isAddress && showSearch,
            onDismissRequest = {
                showSearch = false
            },
            modifier = Modifier
                .background(CakkieBackground, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.9f)
                .height(300.dp)
                .align(CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                CakkieInputField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = stringResource(id = R.string.search_address_city_state),
                    keyboardType = KeyboardType.Text,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "search",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(6.dp)
                        .height(65.dp)
                )
            }
            addressList.forEach { address ->
                Spacer(modifier = Modifier.height(10.dp))
//                Timber.d("address: ${address.formattedAddress}")
                DropdownMenuItem(onClick = {
                    // Handle item selection
                    onValueChange.invoke(TextFieldValue(address.formattedAddress))
                    scope.launch {
                        val place = getPlaceDetails(address.formattedAddress)
                        if (place != null) {
                            onLocationClick.invoke(place)
                        }
                    }
                    showSearch = false
                }) {

                    Text(
                        text = address.formattedAddress,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .background(CakkieBackground)
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }


            }
        }

    }
}

@Composable
fun CakkieInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    isAddress: Boolean = false,
    onLocationClick: (LocationResult) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    isEditable: Boolean = true,
    showEditIcon: Boolean = false,
    location: Location? = null,
    singleLine: Boolean = true
) {
    val scope = rememberCoroutineScope()
    var visible by remember {
        mutableStateOf(keyboardType != KeyboardType.Password)
    }
    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isEdit by remember {
        mutableStateOf(showEditIcon)
    }
    var showSearch by remember {
        mutableStateOf(false)
    }
    var addressList by remember {
        mutableStateOf(
            listOf<LocationResult>()
        )
    }

    LaunchedEffect(key1 = searchQuery, key2 = location) {
        if (isAddress && searchQuery.text.isEmpty()) {
            addressList = getNearbyAddress(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0
            )
        }
        if (searchQuery.text.isNotEmpty()) {
            addressList = searchAddress(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0,
                searchQuery.text
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (location != null && isAddress) {
//                                Timber.d("address is: "+context.getAddressFromLocation(location))
            scope.launch(Dispatchers.IO) {
                val address = getCurrentAddress(
                    location.latitude, location.longitude
                )
                onValueChange.invoke(
                    address?.formattedAddress ?: ""
                )

                if (address != null) {
                    onLocationClick.invoke(address)
                }
            }
        }
    }
    Column {
        OutlinedTextField(
            isError = isError,
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextColorDark.copy(alpha = 0.5f),

                    )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = singleLine,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Transparent,
                focusedBorderColor = CakkieBrown,
                unfocusedBorderColor = CakkieBrown.copy(alpha = 0.5f),
                disabledBorderColor = Transparent,
                cursorColor = CakkieBrown,
                trailingIconColor = CakkieBrown,
                disabledTrailingIconColor = CakkieBrown.copy(alpha = 0.5f),
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick.invoke()
                                if (isAddress) {
                                    showSearch = !showSearch
                                }
                            }
                        }
                    }
                },
            modifier = modifier
                .fillMaxWidth(),
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    if (visible) {
                        Image(
                            painter = painterResource(id = R.drawable.eye_open),
                            contentDescription = "eye closed",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    visible = !visible
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.eye_closed),
                            contentDescription = "eye closed",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    visible = !visible
                                }
                        )
                    }
                }
                if (isEdit) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "edit",
                        modifier = Modifier.size(16.dp)

                    )
                } else {
                    VisualTransformation.None

                }
                if (isAddress) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "eye closed",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (location != null) {
//                                Timber.d("address is: "+context.getAddressFromLocation(location))
                                    scope.launch(Dispatchers.IO) {
                                        val address = getCurrentAddress(
                                            location.latitude, location.longitude
                                        )
                                        onValueChange.invoke(
                                            address?.formattedAddress ?: ""
                                        )

                                        if (address != null) {
                                            onLocationClick.invoke(address)
                                        }
                                    }
                                }
                            }
                    )
                }
            },
            leadingIcon = leadingIcon,
            visualTransformation = if (!visible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            readOnly = !isEditable
        )
        DropdownMenu(
            expanded = isAddress && showSearch,
            onDismissRequest = {
                showSearch = false
            },
            modifier = Modifier
                .background(CakkieBackground, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .padding(vertical = 5.dp)
                .fillMaxWidth(0.9f)
                .height(300.dp)
                .align(CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                CakkieInputField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = stringResource(id = R.string.search_address_city_state),
                    keyboardType = KeyboardType.Text,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "search",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(6.dp)
                        .height(65.dp)
                )
            }
            addressList.forEach { address ->
                Spacer(modifier = Modifier.height(10.dp))
//                Timber.d("address: ${address.formattedAddress}")
                DropdownMenuItem(onClick = {
                    // Handle item selection
                    onValueChange.invoke(address.formattedAddress)
                    scope.launch {
                        val place = getPlaceDetails(address.formattedAddress)
                        if (place != null) {
                            onLocationClick.invoke(place)
                        }
                    }
                    showSearch = false
                }) {

                    Text(
                        text = address.formattedAddress,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .background(CakkieBackground)
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }


            }
        }

    }
}