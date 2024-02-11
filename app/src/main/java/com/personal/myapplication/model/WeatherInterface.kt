package com.personal.myapplication.model

import okhttp3.OkHttpClient
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
        private val BASE_URL= "http://api.weatherapi.com/v1/"

        fun getInstance() : WeatherInterface {
            val retrofitInstance  = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().cache(null).build())
                .build()

            return retrofitInstance.create(WeatherInterface::class.java)

        }


    }
}