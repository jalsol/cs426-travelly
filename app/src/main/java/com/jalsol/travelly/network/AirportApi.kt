package com.jalsol.travelly.network

import com.jalsol.travelly.domain.model.Airport
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportApi {
    @GET("/airports/all")
    suspend fun getAirports(): List<Airport>

    @GET("/airports")
    suspend fun getAirport(
        @Query("code") code: String
    ): Airport?
}
