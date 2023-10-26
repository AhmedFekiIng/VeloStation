package com.example.stationsvelos.uikit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stationsvelos.R
import com.example.stationsvelos.api.Station

class StationsAdapter(private val onStationClick: (Station) -> Unit) :
    ListAdapter<Station, StationsAdapter.StationViewHolder>(StationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = getItem(position)
        holder.bind(station)
        holder.itemView.setOnClickListener { onStationClick(station) }
    }

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stationName: TextView = itemView.findViewById(R.id.itemStationName)
        private val stationAddress: TextView = itemView.findViewById(R.id.itemStationAddress)

        fun bind(station: Station) {
            stationName.text = station.name
            stationAddress.text = station.address
        }
    }

    private class StationDiffCallback : DiffUtil.ItemCallback<Station>() {
        override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem == newItem
        }
    }
}