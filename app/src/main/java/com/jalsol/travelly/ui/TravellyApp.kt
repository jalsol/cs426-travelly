package com.jalsol.travelly.ui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jalsol.travelly.domain.model.Preferences
import com.jalsol.travelly.domain.serializer.dataStore
import com.jalsol.travelly.ui.screens.AccountOptionsScreen
import com.jalsol.travelly.ui.screens.AccountProfileScreen
import com.jalsol.travelly.ui.screens.BookingScreen
import com.jalsol.travelly.ui.screens.EmptyScreen
import com.jalsol.travelly.ui.screens.HomeScreen
import com.jalsol.travelly.ui.screens.LoginScreen
import com.jalsol.travelly.ui.screens.SignupScreen
import com.jalsol.travelly.ui.screens.TransportBoardingPassScreen
import com.jalsol.travelly.ui.screens.TransportBookingScreen
import com.jalsol.travelly.ui.screens.TransportFilterScreen
import com.jalsol.travelly.ui.screens.TransportFlightScreen
import com.jalsol.travelly.ui.screens.TransportSeatsScreen
import com.jalsol.travelly.ui.screens.global.BottomBar
import com.jalsol.travelly.ui.theme.scaffoldBackgroundColor

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TravellyApp() {
    val navController = rememberNavController()
    val preferences = navController.context.dataStore.data.collectAsState(
        initial = Preferences()
    ).value
    Scaffold(
        containerColor = scaffoldBackgroundColor(isSystemInDarkTheme()),
        bottomBar = {
            if (preferences.loggedIn) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = if (!preferences.loggedIn) {
                    Routes.Login
                } else {
                    Routes.Home
                }
            ) {
                composable<Routes.Login> {
                    LoginScreen(navController)
                }
                composable<Routes.Signup> {
                    SignupScreen(navController)
                }
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
                composable<Routes.Transport.Seats> {
                    val args = it.toRoute<Routes.Transport.Seats>()
                    TransportSeatsScreen(navController, args)
                }
                composable<Routes.Transport.BoardingPass> {
                     val args = it.toRoute<Routes.Transport.BoardingPass>()
                     TransportBoardingPassScreen(navController, args)
                }
                composable<Routes.Empty> {
                    EmptyScreen()
                }
            }
        }
    }
}