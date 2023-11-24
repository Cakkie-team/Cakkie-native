package com.cakkie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark

@Composable
fun CakkieInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    isAddress: Boolean = false,
    onLocationClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var visible by remember {
        mutableStateOf(keyboardType != KeyboardType.Password)
    }

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
        modifier = Modifier
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
        }
    )
}