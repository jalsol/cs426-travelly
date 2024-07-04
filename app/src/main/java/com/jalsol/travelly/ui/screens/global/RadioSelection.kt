package com.jalsol.travelly.ui.screens.global

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RadioSelection(
    selected: Int,
    onSelectionChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    composables: List<@Composable (() -> Unit)>,
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        composables.forEachIndexed { index, composable ->
            Button(
                shape = RoundedCornerShape(15.dp),
                onClick = { onSelectionChange(index) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == index) {
                        Color(0xFF089083)
                    } else {
                        Color.White
                    },
                    contentColor = if (selected == index) {
                        Color.White
                    } else {
                        Color(0xFF089083)
                    }
                )
            ) {
                composable()
            }

            if (index < composables.size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioSelectionTest() {
    val composables: List<@Composable (() -> Unit)> = listOf(
        { Text(text = "Economy") },
        { Text(text = "Business") },
    )

    var selected by remember { mutableIntStateOf(0) }
    RadioSelection(
        selected = selected,
        onSelectionChange = { selected = it },
        composables = composables,
//        modifier = Modifier.wrapContentWidth(unbounded = true)
    )
}