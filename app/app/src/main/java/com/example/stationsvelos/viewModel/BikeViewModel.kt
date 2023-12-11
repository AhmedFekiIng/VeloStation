package com.example.stationsvelos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.example.stationsvelos.BuildConfig
import com.example.stationsvelos.api.Station
import com.example.stationsvelos.data.BikeRepository


class BikeViewModel : ViewModel() {
    private val apiKey = BuildConfig.API_KEY
    private val repository = BikeRepository(apiKey)

    private val _stations = MutableLiveData<List<Station>>()
    // use of Live Data
    val stations: LiveData<List<Station>> get() = _stations

    private var isLoading = false

    fun loadStations() {
        Log.d("BikeViewModel", "loadStations")
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val stationsResult = repository.getStations()
                _stations.postValue(stationsResult)
                Log.d("BikeViewModel", "Stations loaded successfully. Count: ${stationsResult.size}")
            } catch (e: Exception) {
                Log.e("BikeViewModel", "Error loading stations: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    private val _stationDetail = MutableLiveData<Station>()
    val stationDetail: LiveData<Station> get() = _stationDetail

    fun getStationDetail(stationId: Int, contractName: String) {
        viewModelScope.launch {
            Log.d("BikeViewModel", "Getting station detail for station number: $stationId, contract: $contractName")

            val stationDetailResult = repository.getStationDetail(stationId, contractName)
            _stationDetail.postValue(stationDetailResult)
        }
    }
}