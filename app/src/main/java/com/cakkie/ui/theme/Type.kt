package com.cakkie.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cakkie.R

// Set of Material typography styles to start with
val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = TextColorDark
        ),
        bodySmall = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(600),
                fontSize = 10.sp,
                lineHeight = 16.sp,
                color = TextColorDark
        ),
        titleLarge = TextStyle(
                fontFamily = FontFamily(Font(R.font.playfairdisplay_medium)),
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                lineHeight = 30.sp,
                letterSpacing = 0.sp,
                color = TextColorDark
        ),
        labelMedium = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                color = TextColorDark
        ),
        bodyMedium = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(600),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                color = TextColorDark
        ),
        labelLarge = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(600),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.sp,
                color = TextColorDark
        ),
        labelSmall = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(400),
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.sp,
                color = TextColorDark
        ),
        displaySmall = TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontWeight = FontWeight(400),
                fontSize = 8.sp,
                lineHeight = 16.8.sp,
                color = Color.White
        ),
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)