package com.cakkie.ui.screens.settings.bottomUI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.theme.CakkieBrown
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun MessageItem(
    navigator: DestinationsNavigator
) {
    var processing by remember {
        mutableStateOf(false)
    }
    val radioButtons = remember {
        mutableStateListOf(
            MessageToggledInfo(
                isChecked = false, text = "YES"
            ),
            MessageToggledInfo(
                isChecked = false, text = "NO"
            ),

            )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "approved",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(20.dp)
                    .padding(horizontal = 5.dp)


            )
            Text(
                text = stringResource(id = R.string.messages_notifications),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        Text(
            text = stringResource(id = R.string.message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            fontSize = 10.sp
        )

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
        Spacer(modifier = Modifier.height(20.dp))

        CakkieButton(
            Modifier
                .height(50.dp)
                .width(328.dp),
            processing = processing,
            text = stringResource(id = R.string.sure)
        ) {
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

}
data class MessageToggledInfo(
    val isChecked: Boolean,
    val text: String
)