package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.RadioSelection
import com.jalsol.travelly.ui.screens.global.toPx
import com.jalsol.travelly.ui.theme.peach
import com.jalsol.travelly.ui.theme.teal
import com.jalsol.travelly.ui.theme.textColor

@Composable
fun TransportSeatsScreen(
    navHostController: NavHostController,
    args: Routes.Transport.Seats
) {
    var selectedPassenger by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBar(
            navHostController = navHostController,
            title = "Select Seats"
        )

        Text(
            text = "Traveller",
            color = textColor(isSystemInDarkTheme())
        )

        val composables: List<@Composable (() -> Unit)> = (1..args.passengers).map { index ->
            {
                Text(text = "$index")
            }
        }
        RadioSelection(
            selected = selectedPassenger,
            onSelectionChange = { selectedPassenger = it },
            composables = composables
        )

        val colorLabels = listOf(
            (0xFFFEA36B to "Selected"),
            (0xFF089083 to "Booked"),
            (0xFFB7DFDB to "Available"),
        )
        Row {
            colorLabels.forEach { (color, label) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "■",
                        color = Color(color),
                        modifier = Modifier.padding(end = 16.dp),
                        fontSize = 18.sp
                    )
                    Text(
                        text = label,
                        modifier = Modifier.padding(end = 8.dp),
                        color = textColor(isSystemInDarkTheme())
                    )
                }
            }
        }

        val selectedSeats = remember {
            mutableStateListOf<Pair<Int, Int>>().apply {
                repeat(args.passengers) {
                    add(Pair(0, 0))
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (row in 0..22) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for (col in 1..4) {
                        if (row != 0) {
                            val isSelected = selectedSeats[selectedPassenger] == Pair(row, col)
                            val isBooked = !isSelected && selectedSeats.contains(Pair(row, col))
                            val buttonColor = when {
                                isSelected -> colorLabels[0].first
                                isBooked -> colorLabels[1].first
                                else -> colorLabels[2].first
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(15.dp.toPx()),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(buttonColor)
                                ),
                                onClick = {
                                    selectedSeats[selectedPassenger] = Pair(row, col)
                                },
                                content = {}
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ABCD".substring(col - 1, col),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor(isSystemInDarkTheme())
                                )
                            }
                        }

                        if (col == 2) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(2f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (row != 0) {
                                    Text(
                                        text = "$row",
                                        fontSize = 18.sp,
                                        color = textColor(isSystemInDarkTheme())
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val seatPosition = if (selectedSeats[selectedPassenger] == Pair(0, 0)) {
                    "Not selected"
                } else {
                    "${selectedSeats[selectedPassenger].first}${"ABCD"[selectedSeats[selectedPassenger].second - 1]}"
                }
                Text(
                    text = "Your seat",
                    color = teal(!isSystemInDarkTheme())
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Traveller ${selectedPassenger + 1}/Seat $seatPosition",
                    fontWeight = FontWeight.Bold,
                    color = textColor(isSystemInDarkTheme())
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total price",
                    color = teal(!isSystemInDarkTheme())
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${args.price}",
                    fontWeight = FontWeight.Bold,
                    color = textColor(isSystemInDarkTheme())
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp.toPx()),
                colors = ButtonDefaults.buttonColors(
                    containerColor = peach(isSystemInDarkTheme()),
                    contentColor = textColor(!isSystemInDarkTheme())
                ),
                onClick = {
                    navHostController.navigate(Routes.Transport.BoardingPass(
                        from = args.from,
                        fromCode = args.fromCode,
                        to = args.to,
                        toCode = args.toCode,
                        date = args.date,
                        time = args.time,
                        classType = args.classType,
                        price = args.price,
                        flightNumber = args.flightNumber,
                        seats = selectedSeats.map {
                            "${it.first}${"ABCD"[it.second - 1]}"
                        }
                    ))
                },
            ) {
                Text(
                    text = "Continue",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
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
fun TransportSeatsScreenPreview() {
    val navHostController = rememberNavController()
    val args = Routes.Transport.Seats(
        from = "Los Angeles",
        fromCode = "LAX",
        to = "New York",
        toCode = "JFK",
        date = "2022-12-31",
        time = "12:00",
        passengers = 10,
        classType = "Economy",
        price = 50f,
        flightNumber = "1234",
    )

    TransportSeatsScreen(navHostController = navHostController, args = args)
}