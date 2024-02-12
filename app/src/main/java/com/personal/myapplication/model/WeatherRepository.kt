package com.personal.myapplication.model


class WeatherRepository {

    suspend fun getWeatherDetails(key: String, cityName: String): WeatherResult {

        val result= WeatherInterface.getInstance().getWeather(key,cityName)

        return result

    }

}