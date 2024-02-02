package com.cakkie.utill

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun String.formatDateTime(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("d MMM, h:mm a", Locale.US)

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: "")
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.formatDate(): String {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatter.parse(this) ?: Date()
        val now = Date()
        val diff = now.time - date.time

        when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "just now"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} mins ago"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} hours ago"
            diff < TimeUnit.DAYS.toMillis(7) -> if (TimeUnit.MILLISECONDS.toDays(diff) > 1) {
                "${TimeUnit.MILLISECONDS.toDays(diff)} days ago"
            } else "${TimeUnit.MILLISECONDS.toDays(diff)} day ago"

            date.year == now.year -> SimpleDateFormat("d MMM, h:mm a", Locale.getDefault()).format(
                date
            )

            else -> SimpleDateFormat("d MMM, yyyy, h:mm a", Locale.getDefault()).format(date)
        }
    } catch (e: ParseException) {
        "Invalid date"
    }
}

