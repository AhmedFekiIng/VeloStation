package com.example.stationsvelos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitClient {
    private const val BASE_URL = "https://api.jcdecaux.com/vls/v3/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("stations")
    suspend fun getStations(@Query("apiKey") apiKey: String): List<Station>


    @GET("stations/{stationNumber}")
    suspend fun getStationDetail(
        @Path("stationNumber") stationNumber: Int,
        @Query("contract") contractName: String,
        @Query("apiKey") apiKey: String
    ): Station
}