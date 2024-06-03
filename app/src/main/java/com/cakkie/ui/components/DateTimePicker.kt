package com.cakkie.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun DateTimePicker(
    label: String,
    selectedDate: Calendar,
    onDateTimeSelected: (Calendar) -> Unit
) {
    val context = LocalContext.current
    val currentDate = Calendar.getInstance()
    var dateText by remember { mutableStateOf(TextFieldValue("")) }
    var timeText by remember { mutableStateOf(TextFieldValue("")) }
    var diffText by remember { mutableStateOf("") }

    LaunchedEffect(selectedDate) {
        dateText = TextFieldValue(
            "${selectedDate.get(Calendar.YEAR)}/${selectedDate.get(Calendar.MONTH) + 1}/${
                selectedDate.get(Calendar.DAY_OF_MONTH)
            }"
        )
        timeText =
            TextFieldValue("${selectedDate.get(Calendar.HOUR_OF_DAY)}:${selectedDate.get(Calendar.MINUTE)}")
        updateDifferenceText(currentDate, selectedDate)?.let {
            diffText = it
        }
    }


    CakkieInputField(
        value = TextFieldValue(diffText),
        onValueChange = { },
        placeholder = "Select Date and Time",
        keyboardType = KeyboardType.Text,
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp)),
        showEditIcon = true,
        isEditable = false,
        onClick = {
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
                            onDateTimeSelected(selectedDate)
                            updateDifferenceText(currentDate, selectedDate)?.let {
                                diffText = it
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
        }

    )
}

fun updateDifferenceText(currentDate: Calendar, selectedDate: Calendar): String? {
    val diffInMillis = selectedDate.timeInMillis - currentDate.timeInMillis
    if (diffInMillis <= 0) return null

    val days = diffInMillis / (1000 * 60 * 60 * 24)
    val hours = (diffInMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)

    return if (days > 0) {
        "$days days and $hours hours from now"
    } else {
        "$hours hours from now"
    }
}
