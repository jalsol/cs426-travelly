package com.jalsol.travelly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.global.Ticket

private data class Entry(
    val title: String,
    val image: Int,
    val onClick: () -> Unit
)

@Composable
fun BookingScreen(navHostController: NavHostController) {
    val entries = listOf(
        Entry("Trips", R.drawable.booking_trip) { navHostController.navigate(Routes.Empty) },
        Entry("Hotel", R.drawable.booking_hotel) { navHostController.navigate(Routes.Empty) },
        Entry("Transport", R.drawable.booking_transport) { navHostController.navigate(Routes.Transport.Booking) },
        Entry("Events", R.drawable.booking_events) { navHostController.navigate(Routes.Empty) },
    )

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "Header") {
            Text(
                text = "Booking",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }

        entries.forEach {
            item(key = it.title) {
                Ticket(
                    top = {
                        Image(
                            painter = painterResource(id = it.image),
                            contentDescription = it.title,
                        )
                    },
                    bottom = {
                        Text(
                            text = it.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                     },
                    onClick = it.onClick,
                    height = 270.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookingScreenPreview() {
    val navHostController = rememberNavController()
    BookingScreen(navHostController = navHostController)
}