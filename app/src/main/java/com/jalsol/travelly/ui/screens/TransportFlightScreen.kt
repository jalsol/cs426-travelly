package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.LinearDatePicker
import com.jalsol.travelly.ui.screens.global.Ticket
import com.jalsol.travelly.ui.screens.global.rememberLinearDatePickerState
import com.jalsol.travelly.ui.theme.teal
import com.jalsol.travelly.ui.theme.textColor
import com.jalsol.travelly.viewmodel.TransportFlightVM
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate

@Composable
fun TransportFlightScreen(
    navHostController: NavHostController,
    args: Routes.Transport.Flight
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBar(
            navHostController = navHostController,
            title = "Flights"
        )

        val state = rememberLinearDatePickerState(LocalDate.parse(args.date))
        val flights by TransportFlightVM.flights.collectAsState()

        LaunchedEffect(args.fromCode, args.toCode, args.date) {
            TransportFlightVM.updateFlights(
                origin = args.fromCode,
                destination = args.toCode,
                date = args.date
            )
        }

        LinearDatePicker(
            state = state,
            onDateSelected = {
                TransportFlightVM.updateFlights(
                    origin = args.fromCode,
                    destination = args.toCode,
                    date = it.toString()
                )
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${flights.size} flights available from ${args.fromCode} to ${args.toCode}",
                color = textColor(isSystemInDarkTheme())
            )
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(40),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFEA36B)
                ),
                onClick = {
                    navHostController.navigate(Routes.Transport.Filter)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo_filter),
                    contentDescription = "Filter",
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            flights.forEach {
                val date = it.departureTime.toLocalDateTime(TimeZone.currentSystemDefault())
                val departure = object {
                    val date = "${date.dayOfMonth}/${date.month.number}"
                    val time = "%02d:%02d".format(date.hour, date.minute)
                }
                Ticket(
                    top = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Tag(
                                modifier = Modifier.weight(1f),
                                label = args.fromCode,
                                content = args.from,
                            )
                            Image(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                painter = painterResource(id = R.drawable.flight_dash),
                                contentDescription = "Flight Dash",
                            )
                            Tag(
                                modifier = Modifier.weight(1f),
                                label = args.toCode,
                                content = args.to,
                            )
                        }
                    },
                    bottom = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Tag(
                                modifier = Modifier.weight(0.8f),
                                label = "Date",
                                content = departure.date
                            )
                            Tag(
                                modifier = Modifier.weight(1f),
                                label = "Departure",
                                content = departure.time
                            )
                            Tag(
                                modifier = Modifier.weight(0.7f),
                                label = "Price",
                                content = "$50"
                            )
                            Tag(
                                modifier = Modifier.weight(0.7f),
                                label = "Number",
                                content = it.flightNumber
                            )
                        }
                    },
                    topRatio = 1f,
                    bottomRatio = 1f,
                    middleRatio = 1f,
                    onClick = {
                        navHostController.navigate(Routes.Transport.Seats(
                            from = args.from,
                            fromCode = args.fromCode,
                            to = args.to,
                            toCode = args.toCode,
                            date = args.date,
                            time = departure.time,
                            passengers = args.passengers,
                            classType = args.classType,
                            price = 50f,
                            flightNumber = it.flightNumber
                        ))
                    },
                    height = 120.dp
                )
            }
        }
    }
}

@Composable
fun Tag(modifier: Modifier = Modifier, label: String, content: String) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            color = teal(!isSystemInDarkTheme())
        )
        Text(
            text = content,
            color = textColor(isSystemInDarkTheme())
        )
    }
}

//@Preview(showBackground = true)
@Composable
private fun TagTest() {
     Tag(label = "NYC", content = "New York")
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TransportFlightScreenPreview() {
    val navHostController = rememberNavController()
    val args = Routes.Transport.Flight(
        from = "Los Angeles",
        fromCode = "LAX",
        to = "New York",
        toCode = "JFK",
        date = "2022-12-31",
        passengers = 1,
        classType = "Economy",
    )
    TransportFlightScreen(
        navHostController = navHostController,
        args = args
    )
}