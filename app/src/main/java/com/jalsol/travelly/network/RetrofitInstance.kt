package com.jalsol.travelly.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val BASE_URL = "http://129.150.63.222:8080/"

object RetrofitInstance {
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val airportApi: AirportApi by lazy {
        createRetrofit().create(AirportApi::class.java)
    }

    val flightApi: FlightApi by lazy {
        createRetrofit().create(FlightApi::class.java)
    }
}