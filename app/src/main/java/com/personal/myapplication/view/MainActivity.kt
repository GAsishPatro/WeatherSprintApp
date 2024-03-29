package com.personal.myapplication.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.personal.myapplication.R
import com.personal.myapplication.model.WeatherResult
import com.personal.myapplication.viewModel.WeatherViewModel
import com.personal.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var weatherVM: WeatherViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
        supportActionBar?.title=getString(R.string.action_title)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLayout.visibility = View.INVISIBLE

        val cityName = intent.getStringExtra("city")!!.toString()

        val pref = getSharedPreferences("cityNames", MODE_PRIVATE)
        val editor = pref.edit()

        weatherVM = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherVM.weatherDetails(cityName)

        weatherVM.isSuccessful.observe(this) { it ->
            when(it) {
                1 -> {
                    editor.putString("city",cityName)
                    editor.apply()
                    weatherVM.weatherResult.observe(this@MainActivity) {
                        bindingValues(it)
                        binding.mainLayout.visibility = View.VISIBLE
                    }
                }
                2 -> {
                    showDialog(this, getString(R.string.city_not_found))
                }
                else -> {
                    showDialog(this, getString(R.string.internet_error))
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh->{
                Toast.makeText(this, getString(R.string.refereshing),Toast.LENGTH_LONG).show()
                refreshFunction()
            }
            R.id.change_city-> finish()
            R.id.exit-> finishAffinity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun refreshFunction() {
        weatherVM.isSuccessful.observe(this) { it ->
            when (it) {
                1 -> {
                    weatherVM.weatherResult.observe(this@MainActivity) {
                        bindingValues(it)
                    }
                }
                else -> showDialog(this, getString(R.string.internet_error))
            }
        }
    }

    private var click=0
    private val onBackPressedCallback= object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if(click==1){
                finishAffinity()
            }
            else{
                click++
                Toast.makeText(this@MainActivity, getString(R.string.exit_press), Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun showDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle(this@MainActivity.getString(R.string.dialog_title))
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}