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

    var weatherResult = MutableLiveData<WeatherResult>()
    var isSucessful = MutableLiveData<Int>()

    fun weatherDetails(key: String, cityName: String){

        viewModelScope.launch(Dispatchers.Default) {
            Log.d("viewModelActivity", "Data Started generating")
            try{
                val details = repository.getWeatherDetails(key, cityName)
                weatherResult.postValue(details)
                Log.d("ViewModelActivity", "Data generation successful")
                isSucessful.postValue(1)
            }
            catch(err: HttpException){
                Log.d("ViewModelActivity", "User inputed a wrong city name")
                isSucessful.postValue(2)
            }
            catch (err: Exception){
                Log.d("ViewModelActivity", "Bad Internet Connection")
                isSucessful.postValue(0)
            }
        }
    }
}