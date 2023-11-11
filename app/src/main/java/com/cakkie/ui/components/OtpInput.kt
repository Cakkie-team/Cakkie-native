package com.cakkie.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error

@Composable
fun OtpInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(0, 1, 2, 3).map {
            Box(
                modifier = Modifier
                    .clickable { focusRequester.requestFocus() }
                    .border(
                        BorderStroke(1.dp, if (isError) Error else CakkieBrown),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (value.text.length > it) value.text[it].toString() else "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    TextField(
        value = value.copy(text = value.text.take(4)), // Limit to 4 characters
        onValueChange = {
            if (it.text.length <= 4) {
                onValueChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .focusRequester(focusRequester)
            .alpha(0f) // Make the TextField invisible)
    )
}