package com.jalsol.travelly.ui.screens.global

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    dateFormat: String,
    label: String
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val confirmEnabled by remember {
        derivedStateOf {
            datePickerState.selectedDateMillis != null
        }
    }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            isDialogVisible = true
        },
    ) {
        CustomTextField(
            value = if (datePickerState.selectedDateMillis != null) {
                SimpleDateFormat(
                    dateFormat,
                    Locale.getDefault()
                ).format(datePickerState.selectedDateMillis)
            } else {
                ""
            },
            onValueChange = {},
            enabled = false,
            label = { Text(label) }
        )
    }
    ShowDateDialog(
        state = datePickerState,
        isDialogVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        confirmButtonAction = {
            TextButton(
                onClick = { isDialogVisible = false },
                enabled = confirmEnabled
            ) {
                Text("OK")
            }
        },
        dismissButtonAction = {
            TextButton(
                onClick = { isDialogVisible = false }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDateDialog(
    state: DatePickerState,
    onDismiss: () -> Unit,
    confirmButtonAction: @Composable () -> Unit,
    dismissButtonAction: @Composable () -> Unit,
    isDialogVisible: Boolean,
) {
    if (isDialogVisible) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = confirmButtonAction,
            dismissButton = dismissButtonAction
        ) {
            DatePicker(state = state, showModeToggle = false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DatePickerPreview() {
    val datePickerState = rememberDatePickerState()
    DateField(
        datePickerState = datePickerState,
        dateFormat = "MMM dd, yyyy",
        label = "Departure"
    )
}
