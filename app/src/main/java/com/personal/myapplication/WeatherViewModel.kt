package com.personal.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class WeatherViewModel(application: Application):AndroidViewModel(application) {

    val repository = WeatherRepository()

    val key="d1c2ffb7fe6c48c1a16103229240402"

    var weatherResult = MutableLiveData<WeatherResult>()
    var isSucessful = MutableLiveData<Int>()

    fun weatherDetails(cityName: String){

        viewModelScope.launch(Dispatchers.Default) {
            Log.d("viewModelActivity", "Data Started generating")
            try{
                val details = repository.getWeatherDetails(key, cityName)
                weatherResult.postValue(details)
                isSucessful.postValue(1)
                Log.d("ViewModelActivity", "Data generation successful")
            }
            catch(err: HttpException){
                isSucessful.postValue(0)
                Log.d("ViewModelActivity", "User inputed a wrong city name \n ${err.message}")
            }
            catch (err: Exception){
                isSucessful.postValue(2)
                Log.d("ViewModelActivity", "Bad Internet Connection  \n" +
                        " ${err.message}")
            }
        }
    }
}