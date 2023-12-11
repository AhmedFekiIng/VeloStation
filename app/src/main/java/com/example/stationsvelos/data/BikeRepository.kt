package com.example.stationsvelos.data

import com.example.stationsvelos.api.RetrofitClient
import android.util.Log
import com.example.stationsvelos.api.Station

class BikeRepository(private val apiKey: String) {
    private val apiService = RetrofitClient.apiService

    suspend fun getStations(): List<Station> {
        return apiService.getStations(apiKey)
    }


    suspend fun getStationDetail(stationId: Int, contractName: String): Station {
        Log.d("ApiRequest", "Get station detail for station number: $stationId, contract: $contractName")
        val stationDetail = apiService.getStationDetail(stationId, contractName, apiKey)
        Log.d("ApiRequest", "Received station detail: $stationDetail")
        return stationDetail

    }
}