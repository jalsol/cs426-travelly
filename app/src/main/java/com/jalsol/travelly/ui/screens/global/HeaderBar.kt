package com.jalsol.travelly.ui.screens.global

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.ui.theme.textColor

@Composable
fun HeaderBar(
    navHostController: NavHostController,
    title: String
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                modifier = Modifier.clickable(onClick = { navHostController.navigateUp() }),
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back Arrow",
                tint = textColor(isSystemInDarkTheme())
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center),
            color = textColor(isSystemInDarkTheme())
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HeaderBarPreview() {
    val navHostController = rememberNavController()
    HeaderBar(navHostController = navHostController, title = "Title")
}