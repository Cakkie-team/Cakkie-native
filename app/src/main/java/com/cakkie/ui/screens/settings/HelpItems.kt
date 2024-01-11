package com.cakkie.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieLightBrown
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpItems(navigator: DestinationsNavigator) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var processing by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CakkieLightBrown)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.problem),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.report_a_problem),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                }
            }

            Image(
                painterResource(id = R.drawable.arrow_right),
                contentDescription = "expand",
                modifier = Modifier
                    .size(height = 24.dp, width = 24.dp)
                    .clickable {
                        isExpanded = true
                    }


            )


            if (isExpanded) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { isExpanded = false },
                    containerColor = CakkieBackground)
                {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Unspecified)
                            .padding(horizontal = 32.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.report_a_problem),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = stringResource(id = R.string.report_problem_message),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(19.dp))

                        CakkieInputField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = "Type Something",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .size(height = 129.dp, width = 330.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                        Spacer(modifier = Modifier.height(22.dp))

                        CakkieButton(
                            Modifier.height(50.dp),
                            processing = processing,
                            text = stringResource(id = R.string.done)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.help),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.help_center),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                }
            }

            Image(
                painterResource(id = R.drawable.arrow_right),
                contentDescription = "expand",
                modifier = Modifier.size(height = 24.dp, width = 24.dp)


            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.contact),
                    contentDescription = "notification",
                    modifier = Modifier.size(height = 24.dp, width = 24.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.contact_us),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                }
            }

            Image(
                painterResource(id = R.drawable.arrow_right),
                contentDescription = "expand",
                modifier = Modifier
                    .size(height = 24.dp, width = 24.dp)
                    .clickable {
                        expanded = true
                    }

            )

            if (expanded) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { expanded = false },
                    containerColor = CakkieBackground)

                {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Unspecified)
                            .padding(horizontal = 32.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.conatct_cakkie),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = stringResource(id = R.string.contact_cakkie_message),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(19.dp))

                        CakkieButton(
                            Modifier.height(50.dp),
                            processing = processing,
                            text = stringResource(id = R.string.call_us)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        CakkieButton(
                            Modifier.height(50.dp),
                            processing = processing,
                            text = stringResource(id = R.string.send_us_an_email)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        CakkieButton(
                            Modifier.height(50.dp),
                            processing = processing,
                            text = stringResource(id = R.string.send_us_an_instagram_message)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(38.dp))

                    }
                }
            }
        }

    }
}