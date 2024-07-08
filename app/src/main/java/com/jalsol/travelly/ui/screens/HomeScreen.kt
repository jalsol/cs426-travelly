package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.home.MenuButton
import com.jalsol.travelly.ui.screens.home.MenuSearchBar
import com.jalsol.travelly.ui.theme.textColor

private data class MenuButtonEntry(
    val onClick: () -> Unit,
    val resId: Int,
    val label: String
)

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val menuButtons = listOf(
        MenuButtonEntry(
            onClick = { /*TODO*/ },
            resId = R.drawable.logo_globe,
            label = "Trips"
        ),
        MenuButtonEntry(
            onClick = { /*TODO*/ },
            resId = R.drawable.logo_hotel,
            label = "Hotel"
        ),
        MenuButtonEntry(
            onClick = { navHostController.navigate(Routes.Transport.Booking) },
            resId = R.drawable.logo_plane,
            label = "Transport"
        ),
        MenuButtonEntry(
            onClick = { /*TODO*/ },
            resId = R.drawable.logo_confetti,
            label = "Events"
        )
    )

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Explore the beautiful world!",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = textColor(isSystemInDarkTheme())
        )
        MenuSearchBar()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Booking Services",
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            menuButtons.forEach { entry ->
                MenuButton(
                    onClick = entry.onClick,
                    legend = {
                        Image(
                            painter = painterResource(id = entry.resId),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(textColor(!isSystemInDarkTheme()))
                        )
                    },
                    label = entry.label
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreview() {
    val navHostController = rememberNavController()
    HomeScreen(navHostController = navHostController)
}