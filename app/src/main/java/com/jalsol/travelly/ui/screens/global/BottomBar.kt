package com.jalsol.travelly.ui.screens.global

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes


private data class BottomBarItem(
    val resId: Int,
    val label: String
)

private val bottomBarItems = listOf(
    BottomBarItem(R.drawable.logo_home, "Home"),
    BottomBarItem(R.drawable.logo_ticket, "Booking"),
    BottomBarItem(R.drawable.logo_bell, "Notifications"),
    BottomBarItem(R.drawable.logo_person_thick, "Account")
)

private fun switchBottomBar(
    navController: NavHostController,
    index: Int
) {
    when (index) {
        0 -> navController.navigate(Routes.Home)
        1 -> navController.navigate(Routes.Booking)
        3 -> navController.navigate(Routes.Account.Options)
        else -> navController.navigate(Routes.Empty)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    var selected by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                shadowElevation = 200f
            },
        containerColor = Color.White,
    ) {
        bottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = (selected == index),
                onClick = {
                    selected = index
                    switchBottomBar(navController, index)
                },
                modifier = Modifier.weight(if (selected == index) 2f else 1f),
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFFFDDA2),
                ),
                icon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = item.resId),
                            contentDescription = item.label
                        )
                        if (selected == index) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = item.label,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            )
        }
    }
}
