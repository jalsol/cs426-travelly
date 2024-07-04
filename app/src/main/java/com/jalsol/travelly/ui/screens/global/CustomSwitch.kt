package com.jalsol.travelly.ui.screens.global

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    colorChecked: Color = Color(0xFF089083),
    colorUnchecked: Color = Color.White,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        onClick = { onCheckedChange(!checked) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (checked) {
                colorChecked
            } else {
                colorUnchecked
            },
            contentColor = if (checked) {
               colorUnchecked
            } else {
                colorChecked
            }
        )
    ) {
        content()
    }
}

@Preview
@Composable
fun CustomSwitchPreview() {
    var checked by remember { mutableStateOf(true) }
    CustomSwitch(
        checked = checked,
        onCheckedChange = { checked = it }
    ) {
        Text(text = "Switch")
    }
}