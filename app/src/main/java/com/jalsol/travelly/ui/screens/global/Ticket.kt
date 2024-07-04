package com.jalsol.travelly.ui.screens.global

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jalsol.travelly.R

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun Ticket(
    top: @Composable (() -> Unit),
    bottom: @Composable (() -> Unit),
    middle: @Composable (() -> Unit)? = null,
    topRatio: Float = 0.85f,
    bottomRatio: Float = 0.15f,
    middleRatio: Float? = null,
    height: Dp? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (height != null) {
                    Modifier.height(height)
                } else {
                    Modifier
                }
            )
            .clickable(enabled = true) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(topRatio)
                .clip(TicketTopShape(10.dp.toPx()))
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            top()
        }

        DottedLine()

        if (middle != null && middleRatio != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(middleRatio)
                    .clip(TicketMiddleShape(10.dp.toPx()))
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                middle()
            }

            DottedLine()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(bottomRatio)
                .clip(TicketBottomShape(10.dp.toPx()))
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            bottom()
        }
    }
}

@Composable
private fun DottedLine() {
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
        drawLine(
            color = Color.Black,
            start = Offset(10.dp.toPx(), 0f),
            end = Offset(size.width - 10.dp.toPx(), 0f),
            strokeWidth = 5f,
            pathEffect = pathEffect
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TicketTest() {
    Ticket(
        top = {
            Image(
                painter = painterResource(id = R.drawable.booking_trip),
                contentDescription = "Trip",
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp)
            )
        },
        middle = {
            Image(
                painter = painterResource(id = R.drawable.booking_hotel),
                contentDescription = "Hotel",
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp)
            )
        },
        bottom = {
            Text(
                text = "Trips",
                fontWeight = FontWeight.Medium,
            )
        },
        topRatio = 0.5f,
        bottomRatio = 0.5f,
        middleRatio = 0.5f
    )
}