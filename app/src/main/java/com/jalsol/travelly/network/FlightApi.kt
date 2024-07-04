package com.jalsol.travelly.network

import com.jalsol.travelly.domain.model.Flight
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApi {
    @GET("/flights")
    suspend fun getFlights(
        @Query("date") date: String
    ): List<Flight>

    @GET("/flights")
    suspend fun getFlights(
        @Query("date") date: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String
    ): List<Flight>

    @GET("/flights")
    suspend fun getFlightsByDestination(
        @Query("date") date: String,
        @Query("destination") code: String
    ): List<Flight>

    @GET("/flights")
    suspend fun getFlightsByOrigin(
        @Query("date") date: String,
        @Query("origin") code: String
    ): List<Flight>

    @GET("/flights")
    suspend fun getFlightsByFlightNumber(
        @Query("date") date: String,
        @Query("flightNumber") flightNumber: String
    ): List<Flight>
}
