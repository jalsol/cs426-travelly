package com.jalsol.travelly.ui.screens.global

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit),
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        CustomTextField(
            modifier = modifier.menuAnchor(),
            readOnly = true,
            value = value,
            onValueChange = {},
            label = label,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun DropDownListTest() {
    var selection by remember { mutableStateOf("") }

    DropDownList(
        label = { Text("Departure") },
        options = listOf("Option 1", "Option 2", "Option 3"),
        value = selection,
        onValueChange = { selection = it }
    )
}