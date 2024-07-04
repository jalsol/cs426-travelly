package com.jalsol.travelly.ui.screens.global

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit.DAYS
import java.util.Locale

@Composable
fun LinearDatePicker(
    state: LinearDatePickerState = rememberLinearDatePickerState(LocalDate.now()),
    pastDaysCount: Int = 30,
    todayLabel: @Composable BoxScope.() -> Unit = {},
    onDateSelected: (LocalDate) -> Unit
) {
    val startDate by remember { mutableStateOf(state.selectedDate.minusDays(pastDaysCount.toLong())) }
    val selectedDateIndex = DAYS.between(startDate, state.selectedDate).toInt()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val fullyVisibleIndices: List<Int> by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isEmpty()) {
                emptyList()
            } else {
                val fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()
                val lastItem = fullyVisibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
                if (lastItem.offset + lastItem.size > viewportHeight) {
                    fullyVisibleItemsInfo.removeLast()
                }

                val firstItemIfLeft = fullyVisibleItemsInfo.firstOrNull()
                if (firstItemIfLeft != null &&
                    firstItemIfLeft.offset < layoutInfo.viewportStartOffset
                ) {
                    fullyVisibleItemsInfo.removeFirst()
                }

                fullyVisibleItemsInfo.map { it.index }
            }
        }
    }

    // We don't want smooth scrolling during initial composition
    var isInitialComposition by remember {
        mutableStateOf(true)
    }

    // scroll to the selected date when it changes
    LaunchedEffect(state.shouldScrollToSelectedDate) {
        // Scroll position should at least be 0
        val scrollPosition = (selectedDateIndex - 1 / 2).coerceAtLeast(0)
        val isSelectedDateVisible = fullyVisibleIndices.contains(selectedDateIndex)
        if (state.shouldScrollToSelectedDate) {
            if (isInitialComposition) {
                listState.scrollToItem(scrollPosition)
            } else if (!isSelectedDateVisible) {
                listState.animateScrollToItem(scrollPosition)
            }

            // Reset the shouldScrollToSelectedDate flag
            state.onScrollCompleted()
        }
    }

    LaunchedEffect(Unit) {
        listState.scrollToItem(selectedDateIndex)

        state.onScrollCompleted()
        isInitialComposition = false
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    coroutineScope.launch {
                        state.smoothScrollToDate(LocalDate.now())
                    }
                }
                .wrapContentSize(),
            contentAlignment = Alignment.Center,
        ) {
            todayLabel()
        }

        Spacer(modifier = Modifier.height(4.dp))

        val visibleItemsCount by remember {
            derivedStateOf { listState.layoutInfo.visibleItemsInfo.size }
        }
        val firstVisibleItemIndex by remember {
            derivedStateOf { listState.firstVisibleItemIndex }
        }
        val lastVisibleItemIndex = (firstVisibleItemIndex + visibleItemsCount - 1)
            .coerceAtLeast(0)

        LaunchedEffect(key1 = firstVisibleItemIndex, key2 = lastVisibleItemIndex) {
            val firstVisibleDate = startDate.plusDays(firstVisibleItemIndex.toLong())
            val lastVisibleDate = startDate.plusDays(lastVisibleItemIndex.toLong())

            state.setVisibleDates(firstVisibleDate, lastVisibleDate)
        }

        DatePickerLayout(listState = listState) {
            items(Integer.MAX_VALUE) { position ->
                val date = startDate.plusDays(position.toLong())

                DateCard(
                    date = date,
                    isSelected = (date == state.selectedDate),
                    onDateSelected = {
                        onDateSelected(it)
                        state.smoothScrollToDate(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun DatePickerLayout(
    listState: LazyListState,
    content: LazyListScope.() -> Unit
) {
    val flingBehavior = rememberSnapFlingBehavior(listState)

    LazyRow(
        modifier = Modifier.height(100.dp),
        state = listState,
        content = content,
        flingBehavior = flingBehavior,
    )
}

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .then(
                if (isSelected) {
                    Modifier.background(Color(0xFFFFDDA2))
                } else {
                    Modifier
                },
            )
            .padding(vertical = 4.dp)
            .clickable { onDateSelected(date) }
            .padding(vertical = 2.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Month
        Text(text = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase())

        // Day of month
        Text(
            text = date.dayOfMonth.toString(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
        )

        // Day of week
        Text(text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase())
    }
}