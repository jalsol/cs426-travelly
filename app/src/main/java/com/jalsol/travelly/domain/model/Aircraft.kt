package com.jalsol.travelly.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PassengerCapacity(
    val total: Int,
    val main: Int,
    val first: Int,
)

@Serializable
data class Aircraft(
    val model: String,
    val speed: Int,
    val passengerCapacity: PassengerCapacity,
)