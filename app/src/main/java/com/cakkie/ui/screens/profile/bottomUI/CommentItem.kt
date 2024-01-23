package com.cakkie.ui.screens.profile.bottomUI

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun CommentItem() {
    val radioButtons = remember {
        mutableStateListOf(
            CommentToggledInfo(
                isChecked = false, text = "Public"
            ),
            CommentToggledInfo(
                isChecked = false, text = "Friends of Friends"
            ),

            )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 17.dp)
    ) {

        Text(
            text = stringResource(id = R.string.who_can_comment_on_your_posts),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            radioButtons.forEachIndexed { index, info ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }

                    }
                ) {
                    RadioButton(
                        selected = info.isChecked,
                        onClick = {
                            radioButtons[index] = info.copy(
                                isChecked = info.isChecked
                            )
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = CakkieBrown,
                            unselectedColor = CakkieBrown,
                        )

                    )

                    Text(text = info.text)

                }
            }
        }

    }
    Spacer(modifier = Modifier.height(15.dp))

    CakkieButton(
        Modifier.height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        text = stringResource(id = R.string.done)
    ) {
    }
    Spacer(modifier = Modifier.height(17.dp))

}


data class CommentToggledInfo(
    val isChecked: Boolean,
    val text: String
)