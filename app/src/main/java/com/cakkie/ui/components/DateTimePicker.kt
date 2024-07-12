package com.cakkie.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.TextColorDark
import java.util.Calendar

@Composable
fun DateTimePicker(
    label: String,
    selectedDate: Calendar,
    paddingStart: Dp = 20.dp,
    onDateTimeSelected: (Calendar, Int) -> Unit
) {
    val context = LocalContext.current
    val currentDate = Calendar.getInstance()
    var dateText by remember { mutableStateOf(TextFieldValue("")) }
    var timeText by remember { mutableStateOf(TextFieldValue("")) }
    var diffText by remember { mutableStateOf(Pair("", 0)) }

    LaunchedEffect(selectedDate) {
        dateText = TextFieldValue(
            "${selectedDate.get(Calendar.YEAR)}/${selectedDate.get(Calendar.MONTH) + 1}/${
                selectedDate.get(Calendar.DAY_OF_MONTH)
            }"
        )
        timeText =
            TextFieldValue("${selectedDate.get(Calendar.HOUR_OF_DAY)}:${selectedDate.get(Calendar.MINUTE)}")
        updateDifferenceText(currentDate, selectedDate).let {
            diffText = it
            onDateTimeSelected(selectedDate, it.second)
        }
    }

    Box(
        Modifier
            .padding(start = paddingStart)
            .border(
                width = 1.dp,
                color = CakkieBrown,
                shape = MaterialTheme.shapes.small
            )
            .height(35.dp)
            .clip(MaterialTheme.shapes.small),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .fillMaxWidth()
                .clickable {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            selectedDate.set(Calendar.YEAR, year)
                            selectedDate.set(Calendar.MONTH, month)
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            dateText = TextFieldValue("$year/${month + 1}/$dayOfMonth")

                            val timePickerDialog = TimePickerDialog(
                                context,
                                { _: TimePicker, hourOfDay: Int, minute: Int ->
                                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    selectedDate.set(Calendar.MINUTE, minute)
                                    timeText = TextFieldValue("$hourOfDay:$minute")
                                    updateDifferenceText(currentDate, selectedDate).let {
                                        diffText = it
                                        onDateTimeSelected(selectedDate, it.second)
                                    }
                                },
                                selectedDate.get(Calendar.HOUR_OF_DAY),
                                selectedDate.get(Calendar.MINUTE),
                                false
                            )
                            timePickerDialog.show()
                        },
                        selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH),
                        selectedDate.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.datePicker.minDate = currentDate.timeInMillis
                    datePickerDialog.show()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = diffText.first,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
                color = TextColorDark
            )

            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Dropdown Icon",
                tint = CakkieBrown,
                modifier = Modifier
//                    .rotate(if (expanded) 180f else 0f)
                    .size(20.dp)
            )
        }
    }
}

fun updateDifferenceText(currentDate: Calendar, selectedDate: Calendar): Pair<String, Int> {
    val diffInMillis = selectedDate.timeInMillis - currentDate.timeInMillis
    if (diffInMillis <= 0) return Pair("", 0)

    val days = diffInMillis / (1000 * 60 * 60 * 24)
    val hours = (diffInMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    val totalHours = diffInMillis / (1000 * 60 * 60)
    return Pair(
        if (days > 0) {
            "$days days and $hours hours from now"
        } else {
            "$hours hours from now"
        }, totalHours.toInt()
    )
}
