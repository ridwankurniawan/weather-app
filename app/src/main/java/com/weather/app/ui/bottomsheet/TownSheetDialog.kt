package com.weather.app.ui.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weather.app.R
import com.weather.app.data.model.Town
import com.weather.app.viewmodel.TownViewModel
import com.weather.app.viewmodel.WeatherViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class TownSheetDialog : BottomSheetDialogFragment() {
    private lateinit var townTransactionViewModel: TownViewModel
    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)
    override fun onCreateView(inflater: LayoutInflater,  container: ViewGroup?,  savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.bottom_sheet_add_town,
                container, false)

        townTransactionViewModel = ViewModelProvider(
                this,
                WeatherViewModelFactory()
        ).get(TownViewModel::class.java)

        val btnSaveTown: Button = v.findViewById(R.id.btnSaveTown)
        val name: EditText = v.findViewById(R.id.name)
        val lat: EditText = v.findViewById(R.id.lat)
        val lon: EditText = v.findViewById(R.id.lon)

        btnSaveTown.setOnClickListener {
            uiScope.launch {
                townTransactionViewModel.addTown(Town(name.text.toString(),lat.text.toString().toDouble(),lon.text.toString().toDouble()))
            }
        }
        return v
    }
}