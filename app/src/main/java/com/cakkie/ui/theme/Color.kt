package com.cakkie.ui.theme

import androidx.compose.ui.graphics.Color
import com.cakkie.R

val CakkieBrown = Color(0xFF8B4513)
val CakkieBlue = Color(0xFF0E9DBC)
val CakkieYellow = Color(0xFFFFA500)
val CakkieGreen = Color(0xFF45BA45)
val CakkieLightBrown = Color(0x4D8B4513)
val CakkieOrange = Color(0xFFFFBE86)
val TextColorDark = Color(0xFF2E1706)
val CakkieBackground = Color(0xFFF5F5DC)
val Error = Color(0xFFF50A0A)
val Inactive = Color(0x4D2E1706)


fun BackgroundImageId(isDarkMode: Boolean) =
    if (isDarkMode) R.drawable.background else R.drawable.background