package com.jalsol.travelly.ui

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Empty

    @Serializable
    object Login

    @Serializable
    object Signup

    @Serializable
    object Home

    @Serializable
    object Booking

    object Account {
        @Serializable
        object Options

        @Serializable
        object Profile
    }

    object Transport {
        @Serializable
        object Booking

        @Serializable
        data class Flight(
            val from: String,
            val fromCode: String,
            val to: String,
            val toCode: String,
            val date: String,
            val passengers: Int,
            val classType: String
        )

        @Serializable
        object Filter

        @Serializable
        data class Seats(
            val from: String,
            val fromCode: String,
            val to: String,
            val toCode: String,
            val date: String,
            val time: String,
            val passengers: Int,
            val classType: String,
            val price: Float,
            val flightNumber: String
        )

        @Serializable
        data class BoardingPass(
            val from: String,
            val fromCode: String,
            val to: String,
            val toCode: String,
            val date: String,
            val time: String,
            val classType: String,
            val price: Float,
            val flightNumber: String,
            val seats: List<String>
        )
    }
}
