package com.cakkie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark

@Composable
fun CommentInput(onPost: (String) -> Unit = {}) {
    var comment by remember {
        mutableStateOf(TextFieldValue(""))
    }

    OutlinedTextField(
        value = comment,
        onValueChange = { comment = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.add_comment),
                style = MaterialTheme.typography.bodyLarge,
                color = TextColorDark.copy(alpha = 0.5f)
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            cursorColor = CakkieBrown,
            trailingIconColor = CakkieBrown,
            disabledTrailingIconColor = CakkieBrown.copy(alpha = 0.5f),
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        trailingIcon = {
            Text(
                text = stringResource(id = R.string.post),
                style = MaterialTheme.typography.labelLarge,
                color = if (comment.text.isNotEmpty()) CakkieBrown else CakkieBrown.copy(alpha = 0.5f),
                modifier = Modifier
                    .clickable {
                        if (comment.text.isNotEmpty()) {
                            onPost(comment.text)
                            comment = TextFieldValue("")
                        }
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}