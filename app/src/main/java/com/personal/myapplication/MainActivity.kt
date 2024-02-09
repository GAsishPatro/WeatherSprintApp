package com.personal.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    lateinit var cityTextView: TextView
    lateinit var tempratureTextView: TextView
    lateinit var windTextView: TextView
    lateinit var humidityTextView: TextView
    lateinit var pressureTextView: TextView
    lateinit var conditionTextView: TextView
    lateinit var weatherImage: ImageView
    lateinit var weatherVM: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityTextView = findViewById(R.id.cityT)
        val cityName = intent.getStringExtra("city")!!.toString()

        tempratureTextView=findViewById(R.id.tempratureT)
        windTextView=findViewById(R.id.windT)
        humidityTextView= findViewById(R.id.humidityT)
        pressureTextView=findViewById(R.id.pressureT)
        conditionTextView=findViewById(R.id.conditionT)
        weatherImage=findViewById(R.id.weatherI)

        val key="d1c2ffb7fe6c48c1a16103229240402"

        weatherVM = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherVM.weatherDetails(key, cityName)

        weatherVM.isSucessful.observe(this)
        {
            if (it==1) {
                weatherVM.weatherResult.observe(this@MainActivity) {
                    bindingValues(it)
                }
            } else if(it==2) {
                showDialog(this , "city name error")
                Toast.makeText(this@MainActivity, "City Don't Found",
                    Toast.LENGTH_LONG).show()
                finish()
            }else{
                finish()
                Toast.makeText(this@MainActivity, "Unable to get Details, Check your Network",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bindingValues(it: WeatherResult) {
        val location = "${ (it.location.name) } ,${ (it.location.country) }"
        cityTextView.text = location
        conditionTextView.text = it.current.condition.text
        val temprature = "${it.current.temp_c.toString()} degree C"
        tempratureTextView.text = temprature
        val wind = "${it.current.wind_kph.toString()} Km/Hr"
        windTextView.text = wind
        humidityTextView.text = it.current.humidity.toString()
        pressureTextView.text = it.current.pressure_in.toString()
        val image = "https:" + it.current.condition.icon

        it.current.condition.icon?.let {
            Glide.with(this@MainActivity).load(image).into(weatherImage)
        }
    }

    fun showDialog(context: Context, message:String){
        val builder= AlertDialog.Builder(context)
                    builder.apply {
                        setMessage(message)
                        setPositiveButton("OK"){dialog,
                        which-> finish()
                        }
                    }
        val dialog = builder.create()
        dialog.show()
    }
}