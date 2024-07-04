package com.jalsol.travelly.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.home.MenuButton
import com.jalsol.travelly.ui.screens.home.MenuSearchBar

@Composable
fun HomeScreen(navHostController: NavHostController) {
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
            fontSize = 18.sp
        )
        MenuSearchBar()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Booking Services",
            fontSize = 16.sp,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuButton(
                onClick = { /*TODO*/ },
                legend = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_globe),
                        contentDescription = "Globe"
                    )
                },
                label = "Trips"
            )
            MenuButton(
                onClick = { /*TODO*/ },
                legend = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_hotel),
                        contentDescription = "Hotel"
                    )
                },
                label = "Hotel"
            )
            MenuButton(
                onClick = { navHostController.navigate(Routes.Transport.Booking) },
                legend = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_plane),
                        contentDescription = "Plane"
                    )
                },
                label = "Transport"
            )
            MenuButton(
                onClick = { /*TODO*/ },
                legend = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_confetti),
                        contentDescription = "Confetti"
                    )
                },
                label = "Events"
            )
        }
    }
}