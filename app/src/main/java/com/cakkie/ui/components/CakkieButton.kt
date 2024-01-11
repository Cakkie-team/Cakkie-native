package com.cakkie.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Error

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    text: String,
    processing: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = !processing && enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Error,
            contentColor = Color.White,
            disabledContainerColor = CakkieBrown.copy(alpha = 0.5f),
            disabledContentColor = CakkieBackground
        ),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (processing) {
            CircularProgressIndicator(
                color = CakkieBackground,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically),
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Butt
            )
        } else {
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = CakkieBackground)
        }
    }
}
