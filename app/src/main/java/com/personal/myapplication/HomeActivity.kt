package com.personal.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    lateinit var cityEditText: EditText
    lateinit var weatherButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cityEditText = findViewById(R.id.cityE)
        weatherButton = findViewById(R.id.weatherB)


        if(isNetworkAvailable()){
            Toast.makeText(this,"Connected to Internet",
                Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"No Internet Connection",
                Toast.LENGTH_LONG).show()
        }
        weatherButton.setOnClickListener {
            val cityName = cityEditText.text.toString()
            buttonClickFunction(cityName)
        }

    }

    private fun buttonClickFunction(cityName: String) {
        if (cityName.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("city", cityName)
            startActivity(intent)
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