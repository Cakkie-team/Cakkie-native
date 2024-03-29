package com.cakkie.ui.screens.wallet.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.CakkieLightBrown
import com.cakkie.ui.theme.TextColorDark

@Composable
fun WalletFilter(value: String, onValueChange: (String) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val options = listOf("All", "Pending", "In Progress", "Completed", "Declined")
    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .border(
                width = 1.dp,
                color = CakkieBrown,
                shape = RoundedCornerShape(8)
            )
            .clip(RoundedCornerShape(8))
            .width(120.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .fillMaxWidth()
                .clickable {
                    expanded = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable {
                        expanded = true
                    },
                color = TextColorDark
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = CakkieBrown,
                modifier = Modifier
                    .rotate(if (expanded) 180f else 0f)
                    .size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(width = 120.dp)
                .background(CakkieBackground)
        ) {
            options.forEach { option ->
                Divider(
                    thickness = 1.dp,
                    color = CakkieLightBrown
                )
                DropdownMenuItem(text = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                    colors = MenuDefaults.itemColors(
                        textColor = CakkieBrown
                    ),
                    onClick = {
                        onValueChange.invoke(option)
                        expanded = false
                    }
                )

            }
            Divider(
                thickness = 1.dp,
                color = CakkieLightBrown
            )
        }
    }
}