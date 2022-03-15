package com.naar.nmovies.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.naar.nmovies.R

val Roboto = FontFamily(
    Font(R.font.roboto_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal)
)
val Nunito = FontFamily(
    Font(
        R.font.nunitosans_light,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        textAlign = TextAlign.Start
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

