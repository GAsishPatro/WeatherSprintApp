package com.personal.myapplication.model

import android.util.Log

class WeatherRepository {

    suspend fun getWeatherDetails(key: String, cityName: String): WeatherResult {

        val result= WeatherInterface.getInstance().getWeather(key,cityName)
        Log.d("repositoryActivity", "result json created")
        return result

    }

}