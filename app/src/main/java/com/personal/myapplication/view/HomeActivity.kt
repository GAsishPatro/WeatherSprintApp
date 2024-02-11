package com.personal.myapplication.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.personal.myapplication.R


class HomeActivity : AppCompatActivity() {

    private lateinit var cityEditText: EditText
    private lateinit var weatherButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cityEditText = findViewById(R.id.cityE)
        weatherButton = findViewById(R.id.weatherB)

        weatherButton.setOnClickListener {
            val cityName = cityEditText.text.toString()
            if(isNetworkAvailable()){
                buttonClickFunction(cityName)
            }else{
                Toast.makeText(this,getString(R.string.no_internet),
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun buttonClickFunction(cityName: String) {
        if (cityName.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("city", cityName)
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.city_IsEmpty), Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable():Boolean{
        val cManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cManager.activeNetwork != null
    }
}