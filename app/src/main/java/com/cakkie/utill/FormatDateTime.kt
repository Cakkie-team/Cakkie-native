package com.cakkie.utill

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatDateTime(input: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("d MMM, h:mm a", Locale.US)

    return try {
        val date = inputFormat.parse(input)
        outputFormat.format(date ?: "")
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}