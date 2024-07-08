package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.Routes
import com.jalsol.travelly.ui.screens.global.DateField
import com.jalsol.travelly.ui.screens.global.DropDownList
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.RadioSelection
import com.jalsol.travelly.ui.screens.global.TransparentTextField
import com.jalsol.travelly.ui.theme.peach
import com.jalsol.travelly.ui.theme.textColor
import com.jalsol.travelly.viewmodel.TransportBookingVM
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransportBookingScreen(navHostController: NavHostController) {
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
            title = "Transport Booking"
        )

        var inputFrom by remember { mutableStateOf("") }
        var inputTo by remember { mutableStateOf("") }
        val airportsLabel = TransportBookingVM.airportsLabel.value

        Box {
            Column {
                DropDownList(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("From") },
                    options = airportsLabel,
                    value = inputFrom,
                    onValueChange = { inputFrom = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropDownList(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("To") },
                    options = airportsLabel,
                    value = inputTo,
                    onValueChange = { inputTo = it }
                )
            }

            Button(
                modifier = Modifier
                    .height(50.dp)
                    .aspectRatio(1f)
                    .align(Alignment.CenterEnd)
                    .offset(x = (-32).dp),
                shape = RoundedCornerShape(40),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFEA36B)
                ),
                onClick = { inputFrom = inputTo.also { inputTo = inputFrom } },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo_swap),
                    contentDescription = "Swap",
                    tint = textColor(!isSystemInDarkTheme())
                )
            }
        }

        val departureDate = rememberDatePickerState()
        val returnDate = rememberDatePickerState()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DateField(
                modifier = Modifier.weight(1f),
                datePickerState = departureDate,
                dateFormat = "MMM dd, yyyy",
                label = "Departure"
            )
            Spacer(modifier = Modifier.weight(0.1f))
            DateField(
                modifier = Modifier.weight(1f),
                datePickerState = returnDate,
                dateFormat = "MMM dd, yyyy",
                label = "Return"
            )
        }

        if (departureDate.selectedDateMillis != null && returnDate.selectedDateMillis != null) {
            if (departureDate.selectedDateMillis!! >= returnDate.selectedDateMillis!!) {
                Text(text = "Return date must be after departure date", color = Color.Red)
            }
        }

        Text(
            "Passenger & Luggage",
            color = textColor(isSystemInDarkTheme())
        )

        var adults by remember { mutableStateOf("") }
        var children by remember { mutableStateOf("") }
        var pets by remember { mutableStateOf("") }
        var luggage by remember { mutableStateOf("") }
        Row {
            TransparentTextField(
                modifier = Modifier.weight(1f),
                value = adults,
                onValueChange = { adults = it },
                prefix = {
                    Icon(
                        painter = painterResource(R.drawable.logo_person),
                        contentDescription = "Adult"
                    )
                }
            )
            TransparentTextField(
                modifier = Modifier.weight(1f),
                value = children,
                onValueChange = { children = it },
                prefix = {
                    Icon(
                        painter = painterResource(R.drawable.logo_child),
                        contentDescription = "Children"
                    )
                }
            )
            TransparentTextField(
                modifier = Modifier.weight(1f),
                value = pets,
                onValueChange = { pets = it },
                prefix = {
                    Icon(
                        painter = painterResource(R.drawable.logo_pet),
                        contentDescription = "Pets"
                    )
                }
            )
            TransparentTextField(
                modifier = Modifier.weight(1f),
                value = luggage,
                onValueChange = { luggage = it },
                prefix = {
                    Icon(
                        painter = painterResource(R.drawable.logo_luggage),
                        contentDescription = "Luggage"
                    )
                }
            )
        }

        Text(
            "Class",
            color = textColor(isSystemInDarkTheme())
        )
        val classLabels = listOf("Economy", "Business")
        val classComposables: List<@Composable (() -> Unit)> = classLabels.map {
            { Text(text = it) }
        }
        var classSelected by remember { mutableIntStateOf(0) }
        RadioSelection(
            selected = classSelected,
            onSelectionChange = { classSelected = it },
            composables = classComposables
        )

        Text(
            "Transport",
            color = textColor(isSystemInDarkTheme())
        )
        val transportComposables: List<@Composable (() -> Unit)> = listOf(
            {
                Icon(
                    painter = painterResource(id = R.drawable.logo_plane),
                    contentDescription = "Plane"
                )
            },
            {
                Icon(
                    painter = painterResource(id = R.drawable.logo_boat),
                    contentDescription = "Boat"
                )
            },
            {
                Icon(
                    painter = painterResource(id = R.drawable.logo_train),
                    contentDescription = "Train"
                )
            },
            {
                Icon(
                    painter = painterResource(id = R.drawable.logo_bus),
                    contentDescription = "Bus"
                )
            },
        )
        var transportSelected by remember { mutableIntStateOf(0) }
        RadioSelection(
            selected = transportSelected,
            onSelectionChange = { transportSelected = it },
            composables = transportComposables,
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = peach(isSystemInDarkTheme())
            ),
            onClick = {
                val fromIndex = airportsLabel.indexOfFirst { it == inputFrom }
                val toIndex = airportsLabel.indexOfFirst { it == inputTo }
                val from = TransportBookingVM.airports.value[fromIndex]
                val to = TransportBookingVM.airports.value[toIndex]
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(departureDate.selectedDateMillis!!)
                val passengers = try {
                    adults.toInt()
                } catch (e: NumberFormatException) {
                    1
                }

                navHostController.navigate(
                    Routes.Transport.Flight(
                        from = from.city,
                        fromCode = from.code,
                        to = to.city,
                        toCode = to.code,
                        date = date,
                        passengers = passengers,
                        classType = classLabels[classSelected],
                    )
                )
            }
        ) {
            Text(
                text = "Search",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor(!isSystemInDarkTheme())
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
private fun TransportBookingScreenPreview() {
    val navHostController = rememberNavController()
    TransportBookingScreen(navHostController = navHostController)
}
