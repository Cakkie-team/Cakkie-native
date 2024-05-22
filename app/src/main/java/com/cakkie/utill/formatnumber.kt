package com.cakkie.utill

import java.text.DecimalFormat

fun formatNumber(value: Double, decimals: Int = 2): String {
    val dec = DecimalFormat("#,##0.00")
    fun formatString(prefix: String) = "%.${decimals}f$prefix"
    return when {
        value >= 1_000_000_000_000 -> String.format(formatString("T"), value / 1_000_000_000_000)
        value >= 1_000_000_000 -> String.format(formatString("B"), value / 1_000_000_000)
        value >= 1_000_000 -> String.format(formatString("M"), value / 1_000_000)
        value >= 100_000 -> String.format(formatString("K"), value / 1_000)
        else -> dec.format(value)
    }
}