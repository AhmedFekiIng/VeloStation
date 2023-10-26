package com.example.stationsvelos.uikit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.stationsvelos.viewModel.BikeViewModel
import com.example.stationsvelos.R
import com.example.stationsvelos.api.Station

class StationDetailFragment : Fragment() {
    private val viewModel: BikeViewModel by viewModels()
    private lateinit var stationName: TextView
    private lateinit var stationContractName: TextView
    private lateinit var stationAddress: TextView
    private lateinit var stationNumber: TextView
    private lateinit var stationStatusLabel: TextView
    private lateinit var stationStatus: TextView
    private lateinit var stationLastUpdateLabel: TextView
    private lateinit var stationLastUpdate: TextView
    private lateinit var bankingCheckBox: CheckBox
    private lateinit var bonusCheckBox: CheckBox
    private lateinit var connectedCheckBox: CheckBox
    private lateinit var capacityValue: TextView
    private lateinit var bikesValue: TextView
    private lateinit var standsValue: TextView
    private lateinit var mechanicalBikesValue: TextView
    private lateinit var electricalBikesValue: TextView
    private lateinit var electricalInternalBatteryBikesValue: TextView
    private lateinit var electricalRemovableBatteryBikesValue: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_station_detail, container, false)
        initViews(view)

        val stationId = arguments?.getInt(STATION_ID_KEY)
        val contractName = arguments?.getString(CONTRACT_NAME)

        Log.d("StationDetailFragment", "Getting station detail for station number: $stationId, contract: $contractName")

        stationId?.let { id ->
            viewModel.getStationDetail(id, contractName ?: "")
            observeViewModel()
        }

        return view
    }

    private fun initViews(view: View) {
        stationName = view.findViewById(R.id.stationName)
        stationContractName = view.findViewById(R.id.stationContractName)
        stationAddress = view.findViewById(R.id.stationAddress)
        stationNumber = view.findViewById(R.id.stationNumber)
        stationStatusLabel = view.findViewById(R.id.statusLabel)
        stationStatus = view.findViewById(R.id.stationStatus)
        stationLastUpdateLabel = view.findViewById(R.id.lastUpdateLabel)
        stationLastUpdate = view.findViewById(R.id.stationLastUpdate)
        bankingCheckBox = view.findViewById(R.id.bankingCheckBox)
        bonusCheckBox = view.findViewById(R.id.bonusCheckBox)
        connectedCheckBox = view.findViewById(R.id.connectedCheckBox)
        capacityValue = view.findViewById(R.id.capacityValue)
        electricalBikesValue = view.findViewById(R.id.electricalBikesValue)
        electricalInternalBatteryBikesValue = view.findViewById(R.id.electricalInternalBatteryBikesValue)
        electricalRemovableBatteryBikesValue = view.findViewById(R.id.electricalRemovableBatteryBikesValue)
        bikesValue = view.findViewById(R.id.bikesValue)
        standsValue = view.findViewById(R.id.standsValue)
        mechanicalBikesValue = view.findViewById(R.id.mechanicalBikesValue)
    }

    private fun observeViewModel() {
        viewModel.stationDetail.observe(viewLifecycleOwner) { station ->
            station?.let {
                updateUI(station)
            }
        }
    }

    private fun updateUI(station: Station) {
        stationNumber.text = station.number.toString()
        stationContractName.text = station.contractName
        stationContractName.text = station.contractName
        stationName.text = station.name
        stationAddress.text = station.address
        stationStatus.text = station.status
        stationLastUpdate.text = station.lastUpdate
        bankingCheckBox.isChecked = station.banking
        bonusCheckBox.isChecked = station.bonus
        connectedCheckBox.isChecked = station.connected
        bikesValue.text = station.totalStands.availabilities.bikes.toString()
        capacityValue.text = station.totalStands.capacity.toString()
        standsValue.text = station.totalStands.availabilities.stands.toString()
        mechanicalBikesValue.text = station.totalStands.availabilities.mechanicalBikes.toString()
        electricalBikesValue.text = station.totalStands.availabilities.electricalBikes.toString()
        electricalInternalBatteryBikesValue.text = station.totalStands.availabilities.electricalInternalBatteryBikes.toString()
        electricalRemovableBatteryBikesValue.text = station.totalStands.availabilities.electricalRemovableBatteryBikes.toString()
    }



    companion object {
        private const val STATION_ID_KEY = "station_id"
        private const val CONTRACT_NAME = "contract_name"

        fun newInstance(stationId: Int, contractName: String): StationDetailFragment {
            val fragment = StationDetailFragment()
            val bundle = Bundle().apply {
                putInt(STATION_ID_KEY, stationId)
                putString(CONTRACT_NAME, contractName)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}