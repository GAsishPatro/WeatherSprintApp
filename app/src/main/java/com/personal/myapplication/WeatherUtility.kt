package com.personal.myapplication

data class WeatherUtility(
    val temp_c: Double,
    val wind_kph: Double,
    val pressure_in : Double,
    val humidity : Double,
    val condition: WeatherImage,
)

data class WeatherResult(
    val location: WeatherLocation,
    val current: WeatherUtility
)

data class WeatherImage(
    val text: String,
    val icon: String?
)

data class WeatherLocation(
    val name: String,
    val country: String,
    val localtime: String
)




