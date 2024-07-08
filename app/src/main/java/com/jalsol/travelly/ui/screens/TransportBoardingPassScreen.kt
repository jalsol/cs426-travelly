package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.Ticket
import com.jalsol.travelly.ui.theme.textColor
import io.github.alexzhirkevich.qrose.oned.BarcodeType
import io.github.alexzhirkevich.qrose.oned.rememberBarcodePainter

@Composable
fun TransportBoardingPassScreen(
    navHostController: NavHostController,
    args: Routes.Transport.BoardingPass
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBar(
            navHostController = navHostController,
            title = "Boarding Pass"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            args.seats.forEachIndexed { index, it ->
                Ticket(
                    topRatio = 1f,
                    middleRatio = 0.5f,
                    bottomRatio = 0.6f,
                    height = 500.dp,
                    top = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.boardingpass),
                                contentDescription = null,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = "American Airlines Flight ${args.flightNumber}",
                                fontSize = 20.sp,
                                color = textColor(isSystemInDarkTheme())
                            )
                        }
                    },
                    middle = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Tag(
                                    modifier = Modifier.weight(0.8f),
                                    label = "Date",
                                    content = args.date
                                )
                                Tag(
                                    modifier = Modifier.weight(1f),
                                    label = "Departure",
                                    content = args.time
                                )
                                repeat(2) {
                                    Tag(
                                        modifier = Modifier.weight(0.7f),
                                        label = "",
                                        content = ""
                                    )
                                }
                            }
                        }
                    },
                    bottom = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Tag(
                                    modifier = Modifier.weight(0.8f),
                                    label = "Passenger",
                                    content = "1 Adult"
                                )
                                Tag(
                                    modifier = Modifier.weight(0.7f),
                                    label = "Ticket",
                                    content = "${args.flightNumber}-${index + 1}"
                                )
                                Tag(
                                    modifier = Modifier.weight(0.9f),
                                    label = "Class",
                                    content = args.classType
                                )
                                Tag(
                                    modifier = Modifier.weight(0.7f),
                                    label = "Seat",
                                    content = it
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberBarcodePainter("9780201379624", BarcodeType.EAN13),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(textColor(isSystemInDarkTheme()))
                                )
                                Text(text = "9780201379624")
                            }
                        }
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFEA36B)
            ),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Download ticket",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF202020,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TransportBoardingPassScreenPreview() {
    val navController = rememberNavController()
    TransportBoardingPassScreen(
        navHostController = navController,
        args = Routes.Transport.BoardingPass(
            from = "New York City",
            fromCode = "JFK",
            to = "Chicago",
            toCode = "ORD",
            date = "2022-12-12",
            time = "12:00",
            classType = "Economy",
            price = 50f,
            flightNumber = "6969",
            seats = listOf("1A", "2B")
        )
    )
}