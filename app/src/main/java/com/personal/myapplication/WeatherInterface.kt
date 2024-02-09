package com.personal.myapplication

import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key")key: String,
        @Query("q")cityname: String) : WeatherResult

    companion object{
        val BASE_URL= "http://api.weatherapi.com/v1/"

        fun getInstance(): WeatherInterface{
            val retrofitInstance  = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofitInstance.create(WeatherInterface::class.java)

        }


    }
}