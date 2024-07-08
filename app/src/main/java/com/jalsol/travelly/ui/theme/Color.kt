package com.jalsol.travelly.ui.theme

import androidx.compose.ui.graphics.Color

fun scaffoldBackgroundColor(dark: Boolean): Color {
    return if (dark) {
        Color(0xFF202020)
    } else {
        Color(0xFFFAFAFA)
    }
}

fun backgroundColor(dark: Boolean): Color {
    return if (dark) {
        Color.Black
    } else {
        Color.White
    }
}

fun textColor(isDarkTheme: Boolean): Color {
    return if (isDarkTheme) {
        Color(0xFFFFFFFF)
    } else {
        Color(0xFF000000)
    }
}

fun peach(dark: Boolean): Color {
    return if (dark) {
        Color(0xFFFEA36B)
    } else {
        Color(0xFFFFDDA2)
    }
}

fun teal(dark: Boolean): Color {
    return if (dark) {
        Color(0xFF01635D)
    } else {
        Color(0xFF089083)
    }
}

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)