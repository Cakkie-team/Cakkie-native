package com.cakkie.ui.screens.settings.bottomUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.R
import com.cakkie.ui.components.CakkieButton
import com.cakkie.ui.components.CakkieInputField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ReportProblem(navigator: DestinationsNavigator) {
    var processing by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 41.dp)
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
                            singleLine = false,
                            placeholder = "Type Something",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .size(height = 129.dp, width = 330.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                        Spacer(modifier = Modifier.height(22.dp))

                        CakkieButton(
                            Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            processing = processing,
                            text = stringResource(id = R.string.done)
                        ) {
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }