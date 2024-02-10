package com.personal.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.personal.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var weatherVM: WeatherViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("city")!!.toString()

        binding.mainLayout.visibility = View.INVISIBLE

        weatherVM = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherVM.weatherDetails(cityName)

        weatherVM.isSuccessful.observe(this) { it ->
            when(it) {
                1 -> {
                    weatherVM.weatherResult.observe(this@MainActivity) {
                        binding.mainLayout.visibility = View.VISIBLE
                        bindingValues(it)
                    }
                }
                2 -> {
                    showDialog(this, "city name error")
                }
                else -> {
                    showDialog(this, "Internet problem")
                }
            }
        }
    }

    private fun bindingValues(res: WeatherResult) {
        val location = "${(res.location.name)}, ${(res.location.region)} ,${(res.location.country)}"
        val temperature = "${res.current.temp_c}°C"
        val wind = "${res.current.wind_kph} Km/Hr"
        val humid="${res.current.humidity}%"
        val vis = "${res.current.vis_km} Km"
        val feelsLike = "${res.current.feelslike_c}°C"
        val pressure = "${res.current.pressure_in} mb"
        val lastUpdate = "last updated: ${res.current.last_updated}"
        val image = "https:" + res.current.condition.icon


        binding.apply{
            locationT.text = location
            weatherconditionT.text = res.current.condition.text
            tempratureT.text = temperature
            windspeedT.text = wind
            humidityT.text = humid
            pressureT.text = pressure
            uvT.text = res.current.uv.toString()
            visT.text = vis
            feelsT.text = feelsLike
            lastupdateT.text = lastUpdate
            timeT.text = res.location.localtime
            res.current.condition.icon?.let {
                Glide.with(this@MainActivity).load(image).into(weatherimageT)
            }
        }

    }

    private fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Error while getting Details")
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}