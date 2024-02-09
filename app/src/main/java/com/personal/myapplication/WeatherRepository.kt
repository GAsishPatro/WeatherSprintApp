package com.personal.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WeatherRepository {

    suspend fun getWeatherDetails(key: String, cityName: String): WeatherResult {

        val result= WeatherInterface.getInstance().getWeather(key,cityName)
        Log.d("repositoryActivity", "result json created")
        return result

    }

}