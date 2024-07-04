package com.jalsol.travelly.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Duration(
    val locale: String,
    val hours: Int,
    val minutes: Int,
) : Comparable<Duration> {
    override fun compareTo(other: Duration): Int {
        return (hours * 60 + minutes) - (other.hours * 60 + other.minutes)
    }
}

@Serializable
data class Flight(
    val origin: Airport,
    val destination: Airport,
    val duration: Duration,
    val aircraft: Aircraft,

    val flightNumber: String,
    val distance: Int,
    val departureTime: Instant,
    val arrivalTime: Instant,
)