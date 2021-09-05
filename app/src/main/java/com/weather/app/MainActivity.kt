package com.weather.app

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.materialspinner.MaterialSpinner
import com.squareup.picasso.Picasso
import com.weather.app.data.model.DailyWeather
import com.weather.app.data.model.Town
import com.weather.app.ui.adapter.HorizontalAdapter
import com.weather.app.ui.bottomsheet.TownSheetDialog
import com.weather.app.viewmodel.TownViewModel
import com.weather.app.viewmodel.WeatherViewModel
import com.weather.app.viewmodel.WeatherViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var weatherTransactionViewModel: WeatherViewModel
    private lateinit var townTransactionViewModel: TownViewModel
    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)
    private val listOfTown = arrayListOf<Town>()
    private val listOfDailyWeather = arrayListOf<DailyWeather>()
    private var selectedIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherTransactionViewModel = ViewModelProvider(
                this,
                WeatherViewModelFactory()
        ).get(WeatherViewModel::class.java)

        townTransactionViewModel = ViewModelProvider(
                this,
                WeatherViewModelFactory()
        ).get(TownViewModel::class.java)

        setContentView(R.layout.activity_main)
        //INIT

        val spinner = findViewById<View>(R.id.town_spinner) as MaterialSpinner
        val currentWeatherIcon = findViewById<View>(R.id.currentWeatherIcon) as ImageView
        val temp = findViewById<View>(R.id.temp) as TextView
        val currentWeather = findViewById<View>(R.id.currentWeather) as TextView

        val humidity = findViewById<View>(R.id.humidity) as TextView
        val estimated = findViewById<View>(R.id.estimated) as TextView

        val textDate = findViewById<View>(R.id.textDate) as TextView
        val textPlace = findViewById<View>(R.id.textPlace) as TextView

        val recycler = findViewById<RecyclerView>(R.id.recyclerWeather)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val bottomSheet = TownSheetDialog()
            bottomSheet.show(supportFragmentManager,
                    "ModalBottomSheet")
        }
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)
        btnRefresh.setOnClickListener {
            if (listOfTown.isNotEmpty()){
                uiScope.launch {
                    weatherTransactionViewModel.dailyWeather(listOfTown[selectedIndex].lat!!, listOfTown[selectedIndex].lon!!)
                    textPlace.apply {
                        text = "${listOfTown[selectedIndex].name}, Poland"
                    }
                }
            }
        }
        //setting recycler to horizontal scroll
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = HorizontalAdapter(listOfDailyWeather)

        val date = Date()
        val df: DateFormat = SimpleDateFormat("EEEE, MMMM dd")

        textDate.apply {
            text = df.format(date)
        }


        spinner.setItems(listOfTown)
        spinner.setOnItemSelectedListener { view, position, id, item ->
            selectedIndex = position
            val selected = item as Town
            uiScope.launch {
                textPlace.apply {
                    text = "${selected.name}, Poland"
                }
                weatherTransactionViewModel.dailyWeather(selected.lat!!, selected.lon!!)
            }
        }

        uiScope.launch {
            townTransactionViewModel.getTowns()
        }
        townTransactionViewModel.townResult.observe(this, {
            if (it.isEmpty()){
                uiScope.launch {
                    townTransactionViewModel.addTown(Town("Gdansk", 54.3612063, 18.5499456))
                    townTransactionViewModel.addTown(Town("Warszawa", 52.2330653, 20.9211123))
                    townTransactionViewModel.addTown(Town("Krakow", 50.0468548, 19.9348336))
                    townTransactionViewModel.addTown(Town("Wroclaw", 51.1271647, 16.9218245))
                    townTransactionViewModel.addTown(Town("Lodz", 51.7732033, 19.4105531))
                    townTransactionViewModel.getTowns()
                }
            }else{
                listOfTown.clear()
                listOfTown.addAll(it)
                uiScope.launch {
                    weatherTransactionViewModel.dailyWeather(listOfTown[0].lat!!, listOfTown[0].lon!!)
                    spinner.selectedIndex = 0
                    textPlace.apply {
                        text = "${listOfTown[0].name}, Poland"
                    }
                }
            }



        })
        weatherTransactionViewModel.weatherResult.observe(this, {
            listOfDailyWeather.clear()
            listOfDailyWeather.addAll(it.daily)
            recycler.adapter?.notifyDataSetChanged()
            currentWeather.apply {
                text = it.current.weather[0].description
            }
            val tmp = "${it.current.temp.toInt()}°"
            temp.apply {
                text = tmp
            }

            val hmdty = "${it.current.pressure} hPa."
            humidity.apply {
                text = hmdty
            }

            val estmted = "${it.current.humidity}%"
            estimated.apply {
                text = estmted
            }
            Picasso.get().load(Uri.parse("https://openweathermap.org/img/wn/${it.current.weather[0].icon}@2x.png")).into(currentWeatherIcon)
        })
    }
}