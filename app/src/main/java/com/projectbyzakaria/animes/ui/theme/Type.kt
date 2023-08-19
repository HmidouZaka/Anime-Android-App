package com.projectbyzakaria.animes.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.R

val fontFamily = FontFamily(listOf(
    Font(R.font.montserrat_alternates_bold, FontWeight.Bold),
    Font(R.font.montserrat_alternates_extra_light, FontWeight.ExtraLight),
    Font(R.font.montserrat_alternates_medium, FontWeight.Medium),
    Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.pacifico_regular, FontWeight.Bold),
))



val Typography = Typography(
    defaultFontFamily = fontFamily, h6 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold)
)