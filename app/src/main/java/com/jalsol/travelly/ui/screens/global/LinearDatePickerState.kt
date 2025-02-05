package com.jalsol.travelly.ui.screens.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import java.time.LocalDate

class LinearDatePickerState(
    selectedDate: LocalDate,
    shouldScrollToSelectedDate: Boolean = true,
) {
    private var _initialDate by mutableStateOf(selectedDate, structuralEqualityPolicy())
    private var _shouldScrollToSelectedDate by mutableStateOf(
        shouldScrollToSelectedDate,
    )

    internal fun onScrollCompleted() {
        _shouldScrollToSelectedDate = false
    }

    val shouldScrollToSelectedDate: Boolean
        get() = _shouldScrollToSelectedDate

    val selectedDate: LocalDate
        get() = _initialDate

    fun smoothScrollToDate(date: LocalDate) {
        _shouldScrollToSelectedDate = true
        _initialDate = date
    }

    fun setVisibleDates(firstDate: LocalDate?, lastDate: LocalDate?) {
        _firstVisibleDate = firstDate
        _lastVisibleDate = lastDate
    }

    private var _firstVisibleDate by mutableStateOf<LocalDate?>(null)
    val firstVisibleDate get() = _firstVisibleDate

    private var _lastVisibleDate by mutableStateOf<LocalDate?>(null)
    val lastVisibleDate get() = _lastVisibleDate

    companion object {
        val Saver: Saver<LinearDatePickerState, *> = listSaver(
            save = {
                listOf(
                    it.selectedDate.year,
                    it.selectedDate.monthValue,
                    it.selectedDate.dayOfMonth,
                    it.shouldScrollToSelectedDate.toString(),
                )
            },
            restore = {
                LinearDatePickerState(
                    selectedDate = LocalDate.of(
                        it[0].toString().toInt(), // year
                        it[1].toString().toInt(), // month
                        it[2].toString().toInt(), // day
                    ),

                    shouldScrollToSelectedDate = it[3].toString()
                        .toBoolean(), // shouldScrollToSelectedDate
                )
            },
        )
    }
}

@Composable
fun rememberLinearDatePickerState(initialDate: LocalDate = LocalDate.now()) =
    rememberSaveable(saver = LinearDatePickerState.Saver) { LinearDatePickerState(initialDate) }