package com.personal.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.personal.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var weatherVM: WeatherViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("city")!!.toString()

        binding.mainLayout.visibility = View.INVISIBLE

        weatherVM = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherVM.weatherDetails(cityName)

        weatherVM.isSucessful.observe(this)
        { it ->
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

    private fun bindingValues(it: WeatherResult) {
        val location = "${(it.location.name)} ,${(it.location.country)}"
        binding.locationT.text = location
        binding.weatherconditionT.text = it.current.condition.text
        val temprature = "${it.current.temp_c.toString()}°C"
        binding.tempratureT.text = temprature
        val wind = "${it.current.wind_kph.toString()} Km/Hr"
        binding.windspeedT.text = wind
        binding.humidityT.text = it.current.humidity.toString()
        binding.pressureT.text = it.current.pressure_in.toString()
        binding.timeT.text= it.location.localtime
        val image = "https:" + it.current.condition.icon

        it.current.condition.icon?.let {
            Glide.with(this@MainActivity).load(image).into(binding.weatherimageT)
        }
    }

    fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Error while getting Details")
            setMessage(message)
            setPositiveButton("OK") { dialog,
                                      which ->
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}