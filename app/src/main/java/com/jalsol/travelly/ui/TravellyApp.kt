package com.jalsol.travelly.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jalsol.travelly.ui.screens.AccountOptionsScreen
import com.jalsol.travelly.ui.screens.AccountProfileScreen
import com.jalsol.travelly.ui.screens.BookingScreen
import com.jalsol.travelly.ui.screens.EmptyScreen
import com.jalsol.travelly.ui.screens.HomeScreen
import com.jalsol.travelly.ui.screens.TransportBookingScreen
import com.jalsol.travelly.ui.screens.TransportFilterScreen
import com.jalsol.travelly.ui.screens.TransportFlightScreen
import com.jalsol.travelly.ui.screens.global.BottomBar

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TravellyApp() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color(0xFFFAFAFA),
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.Home
            ) {
                composable<Routes.Home> {
                    HomeScreen(navController)
                }
                composable<Routes.Booking> {
                    BookingScreen(navController)
                }
                composable<Routes.Transport.Booking> {
                    TransportBookingScreen(navController)
                }
                composable<Routes.Account.Options> {
                    AccountOptionsScreen(navController)
                }
                composable<Routes.Account.Profile> {
                    AccountProfileScreen(navController)
                }
                composable<Routes.Transport.Flight> {
                    val args = it.toRoute<Routes.Transport.Flight>()
                    TransportFlightScreen(navController, args)
                }
                composable<Routes.Transport.Filter> {
                    TransportFilterScreen(navController)
                }
                composable<Routes.Empty> {
                    EmptyScreen()
                }
            }
        }
    }
}