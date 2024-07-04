package com.jalsol.travelly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalsol.travelly.domain.model.Flight
import com.jalsol.travelly.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object TransportFlightVM : ViewModel() {
    private val _flights = MutableStateFlow(emptyList<Flight>())
    private var filters = emptyList<(Flight) -> Boolean>()
    private var sortComparator: Comparator<Flight>? = null

    val flights: StateFlow<List<Flight>>
        get() = _flights.map { flightsList ->
            var filteredAndSortedFlights = flightsList
            filters.forEach { filter ->
                filteredAndSortedFlights = filteredAndSortedFlights.filter(filter)
            }
            sortComparator?.let {
                filteredAndSortedFlights = filteredAndSortedFlights.sortedWith(it)
            }
            filteredAndSortedFlights
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateFlights(date: String, origin: String, destination: String) {
        viewModelScope.launch {
            _flights.value = RetrofitInstance.flightApi.getFlights(date, origin, destination)
        }
    }

    fun sortFlightsBy(flightComparator: Comparator<Flight>) {
        sortComparator = flightComparator
    }

    fun filterFlightsBy(filter: (Flight) -> Boolean) {
        filters += filter
    }

    fun resetFilters() {
        filters = emptyList()
        sortComparator = null
    }
}