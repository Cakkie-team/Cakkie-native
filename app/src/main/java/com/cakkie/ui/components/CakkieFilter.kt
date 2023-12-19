package com.cakkie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cakkie.R
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CakkieFilter (){
        var expanded by remember {
            mutableStateOf(false)
        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(120.dp)
            .height(187.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 160.dp, height = 94.dp)
                .background(CakkieBackground)
        ) {
            OutlinedTextField(
                value = "Filters",
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .padding(start = 20.dp)
                    .size(width = 84.dp, height = 28.dp)
                    .background(CakkieBackground),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CakkieBrown,
                )
            )
            ExposedDropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.all),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {  }

                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.in_progress),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {  }

                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.completed),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {  }

                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.pending),
                            style = MaterialTheme.typography.labelSmall,
                            modifier =  Modifier.clickable {  }

                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
                Divider(
                    modifier = Modifier.padding(start = 5.dp),
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.declined),
                        style = MaterialTheme.typography.labelSmall,
                        modifier =  Modifier.clickable {  }
                    )
                },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }
}