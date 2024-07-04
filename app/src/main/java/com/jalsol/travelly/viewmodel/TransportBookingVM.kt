package com.jalsol.travelly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalsol.travelly.domain.model.Airport
import com.jalsol.travelly.network.RetrofitInstance
import kotlinx.coroutines.launch

object TransportBookingVM : ViewModel() {
    val airportsLabel = mutableStateOf<List<String>>(emptyList())
    val airports = mutableStateOf<List<Airport>>(emptyList())

    init {
        viewModelScope.launch {
            val response = RetrofitInstance.airportApi.getAirports()
            airports.value = response
            airportsLabel.value = airports.value.map { "${it.city} (${it.code})" }
        }
    }
}