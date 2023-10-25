package com.example.stationsvelos.uikit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stationsvelos.viewModel.BikeViewModel
import com.example.stationsvelos.R

class StationsFragment : Fragment() {
    private val viewModel: BikeViewModel by viewModels()
    private lateinit var stationsAdapter: StationsAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("StationsFragment", "onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_stations, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        stationsAdapter = StationsAdapter { station ->
            Log.d("StationsFragment", "Getting station detail for station number: ${station.number}, contract: ${station.contractName}")
            val action = StationsFragmentDirections.actionStationsFragmentToStationDetailFragment(stationId = station.number, contractName = station.contractName)
            findNavController().navigate(action)
        }
        recyclerView.adapter = stationsAdapter

        if (!isLoading) {
            isLoading = true
            viewModel.loadStations()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    isLoading = true
                    viewModel.loadStations()
                }
            }
        })

        viewModel.stations.observe(viewLifecycleOwner) { stations ->
            stationsAdapter.submitList(stations)
            isLoading = false
        }

        return view
    }

    companion object {
        fun newInstance(): StationsFragment {
            return StationsFragment()
        }
    }
}