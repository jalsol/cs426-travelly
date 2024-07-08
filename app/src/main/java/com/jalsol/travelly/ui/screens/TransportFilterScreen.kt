package com.jalsol.travelly.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jalsol.travelly.R
import com.jalsol.travelly.ui.screens.global.CustomSwitch
import com.jalsol.travelly.ui.screens.global.CustomTextField
import com.jalsol.travelly.ui.screens.global.HeaderBar
import com.jalsol.travelly.ui.screens.global.RadioSelection
import com.jalsol.travelly.ui.theme.peach
import com.jalsol.travelly.ui.theme.textColor
import com.jalsol.travelly.viewmodel.TransportFlightVM
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.round

@Composable
fun TransportFilterScreen(
    navHostController: NavHostController,
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
            title = "Filter"
        )

        val timeRange = listOf(
            "12AM - 06AM",
            "06AM - 12PM",
            "12PM - 06PM",
            "06PM - 12AM",
        )
        val timeTexts: List<@Composable (() -> Unit)> = timeRange.map {
            { Text(text = it) }
        }

        Text(
            text = "Departure",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )

        var departureSelected by remember { mutableIntStateOf(-1) }
        RadioSelection(
            selected = departureSelected,
            onSelectionChange = { departureSelected = it },
            composables = timeTexts,
        )

        Text(
            text = "Arrival",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )

        var arrivalSelected by remember { mutableIntStateOf(-1) }
        RadioSelection(
            selected = arrivalSelected,
            onSelectionChange = { arrivalSelected = it },
            composables = timeTexts,
        )

        Text(
            text = "Price",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )

        var sliderPosition by remember { mutableStateOf(50f..250f) }
        var inputStart by remember { mutableStateOf("50") }
        var inputEnd by remember { mutableStateOf("250") }
        Column {
            RangeSlider(
                value = sliderPosition,
                steps = 499,
                onValueChange = { range ->
                    val start = round(range.start).toInt()
                    val end = round(range.endInclusive).toInt()
                    if (start < 0) {
                        sliderPosition = 0f..end.toFloat()
                    } else if (end > 500) {
                        sliderPosition = start.toFloat()..500f
                    }
                    if (start >= end) {
                        sliderPosition = end.toFloat()..start.toFloat()
                    }
                    sliderPosition = start.toFloat()..end.toFloat()
                    inputStart = start.toString()
                    inputEnd = end.toString()
                },
                valueRange = 0f..500f,
                onValueChangeFinished = {
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF01635D),
                    activeTrackColor = Color(0xFF089083),
                    activeTickColor = Color(0xFF089083),
                    inactiveTrackColor = Color(0xFFB7DFDB),
                    inactiveTickColor = Color(0xFFB7DFDB),
                )
            )

            Row {
                CustomTextField(
                    value = inputStart,
                    onValueChange = { inputStart = it },
                    label = { Text(text = "From") },
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) {
                                sliderPosition = try {
                                    inputStart.toFloat()..inputEnd.toFloat()
                                } catch (_: Exception) {
                                    0f..sliderPosition.endInclusive
                                }
                            }
                        },
                    prefix = { Text(text = "$") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.weight(0.2f))

                CustomTextField(
                    value = inputEnd,
                    onValueChange = { inputEnd  = it },
                    label = { Text(text = "To") },
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) {
                                sliderPosition = try {
                                    inputStart.toFloat()..inputEnd.toFloat()
                                } catch (_: Exception) {
                                    sliderPosition.start..500f
                                }
                            }
                        },
                    prefix = { Text(text = "$") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Text(
            text = "Facilities",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )

        val facilities = listOf(
            R.drawable.logo_coffee,
            R.drawable.logo_utensils,
            R.drawable.logo_wifi,
            R.drawable.logo_snowflake,
        )

        val facilitiesChecked = remember {
            MutableList(facilities.size) { mutableStateOf(false) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            facilities.mapIndexed { index, resId ->
                CustomSwitch(
                    checked = facilitiesChecked[index].value,
                    onCheckedChange = { facilitiesChecked[index].value = it },
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Icon(painter = painterResource(resId), contentDescription = null)
                }
            }
        }
        Text(
            text = "Sort by",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor(isSystemInDarkTheme())
        )

        var sortByState by remember { mutableIntStateOf(0) }
        val sortLabels = listOf(
            "Arrival time",
            "Departure time",
            "Price",
            "Lowest fare",
            "Duration"
        )
        Column(
            modifier = Modifier
                .selectableGroup()
                .padding(all = 0.dp),
        ) {
            sortLabels.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier
                        .padding(all = 0.dp)
                        .clickable { sortByState = index },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = sortByState == index,
                        onClick = { sortByState = index },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF01635D),
                            unselectedColor = Color(0xFF01635D)
                        )
                    )
                    Text(
                        text = label,
                        modifier = Modifier.padding(start = 8.dp),
                        color = textColor(isSystemInDarkTheme())
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = textColor(!isSystemInDarkTheme()),
                    contentColor = peach(isSystemInDarkTheme()),
                ),
                onClick = {
                    departureSelected = -1
                    arrivalSelected = -1
                    sliderPosition = 50f..250f
                    inputStart = "50"
                    inputEnd = "250"
                    facilitiesChecked.forEach { it.value = false }
                    sortByState = 0
                    TransportFlightVM.resetFilters()
                }
            ) {
                Text(
                    text = "Reset",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = peach(isSystemInDarkTheme()),
                    contentColor = textColor(!isSystemInDarkTheme()),
                ),
                onClick = {
                    TransportFlightVM.resetFilters()
                    if (departureSelected != -1) {
                        TransportFlightVM.filterFlightsBy {
                            val hour = it.departureTime.toLocalDateTime(TimeZone.currentSystemDefault()).hour
                            when (departureSelected) {
                                0 -> hour in 0..5
                                1 -> hour in 6..11
                                2 -> hour in 12..17
                                3 -> hour in 18..23
                                else -> false
                            }
                        }
                    }
                    if (arrivalSelected != -1) {
                        TransportFlightVM.filterFlightsBy {
                            val hour = it.arrivalTime.toLocalDateTime(TimeZone.currentSystemDefault()).hour
                            when (arrivalSelected) {
                                0 -> hour in 0..5
                                1 -> hour in 6..11
                                2 -> hour in 12..17
                                3 -> hour in 18..23
                                else -> false
                            }
                        }
                    }
                    when (sortByState) {
                        0 -> TransportFlightVM.sortFlightsBy(compareBy { it.arrivalTime })
                        1 -> TransportFlightVM.sortFlightsBy(compareBy { it.departureTime })
                        4 -> TransportFlightVM.sortFlightsBy(compareBy { it.duration })
                    }

                    navHostController.popBackStack()
                }
            ) {
                Text(
                    text = "Done",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
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
private fun TransportFilterScreenPreview() {
    val navHostController = rememberNavController()
    TransportFilterScreen(
        navHostController = navHostController
    )
}