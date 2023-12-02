package com.cakkie.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CakkieInputField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    isAddress: Boolean = false,
    onLocationClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    isEditable: Boolean = true
) {
    var visible by remember {
        mutableStateOf(keyboardType != KeyboardType.Password)
    }
    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var showSearch by remember {
        mutableStateOf(false)
    }
    var addressList by remember {
        mutableStateOf(
            listOf(
                "12 Aba Road/Umuahia/Abia",
                "12 Aba Road/Umuahia/Abia",
                "12 Aba Road/Umuahia/Abia"
            )
        )
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
                    color = TextColorDark.copy(alpha = 0.5f)
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
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
                            painter = painterResource(id = R.drawable.eye_closed),
                            contentDescription = "eye closed",
                            modifier = Modifier.clickable {
                                visible = !visible
                            }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.eye_open),
                            contentDescription = "eye closed",
                            modifier = Modifier.clickable {
                                visible = !visible
                            }
                        )
                    }
                }
                if (isAddress) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "eye closed",
                        modifier = Modifier.clickable {
                            onLocationClick.invoke()
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
        if (isAddress && showSearch) {
            Popup(
                onDismissRequest = {
                    showSearch = false
                },
                offset = IntOffset(
                    x = 0,
                    y = 120
                ),
                properties = PopupProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                )
            ) {
                Card(
                    Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CakkieBackground
                    ),
                    elevation = CardDefaults.elevatedCardElevation(),
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp)
                            .padding(16.dp)
                    ) {
                        stickyHeader {
                            CakkieInputField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = stringResource(id = R.string.search_address_city_state),
                                keyboardType = KeyboardType.Text,
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "search",
                                    )
                                },
                            )
                        }
                        items(
                            items = addressList,
                        ) { address ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = address,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Black,
                            )
                        }

                    }
                }
            }
        }
    }
}