package com.personal.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class HomeActivity : AppCompatActivity() {

    lateinit var cityEditText: EditText
    lateinit var weatherButton: Button
    lateinit var weatherVM: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cityEditText = findViewById(R.id.cityE)
        weatherButton = findViewById(R.id.weatherB)
        weatherVM= ViewModelProvider(this).get(WeatherViewModel::class.java)


        weatherButton.setOnClickListener {
            if(isNetworkAvailable()){
                val cityName = cityEditText.text.toString()
                buttonClickFunction(cityName)
            }else{
                Toast.makeText(this,"No Internet Connection",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun buttonClickFunction(cityName: String) {
        if (cityName.isNotEmpty()) {
            weatherVM.weatherDetails(cityName)
            weatherVM.isSucessful.observe(this){
                if (it==1) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("city", cityName)
                    startActivity(intent)
                } else if (it==0) {
                    Toast.makeText(this, "City not found", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "City not found,internet", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Please enter city name", Toast.LENGTH_LONG).show()
        }
    }


    fun isNetworkAvailable():Boolean{
        val cManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cManager.activeNetwork == null)
            return false
        return true
    }
}