package com.jalsol.travelly.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class Airport(
    val code: String,
    val city: String,
    val timezone: String,
    val location: Location,
)
