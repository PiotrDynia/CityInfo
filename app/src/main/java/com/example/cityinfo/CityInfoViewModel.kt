package com.example.cityinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CityInfoViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    private val _state = MutableStateFlow(CityInfoState())
    val state: StateFlow<CityInfoState> = _state.asStateFlow()

    fun onAction(action: CityInfoAction) {
        when(action) {
            is CityInfoAction.updateSearchInput -> {
                _state.update { newState ->
                    newState.copy(
                        searchInput = action.input
                    )
                }
            }
            CityInfoAction.SearchCityInfo -> {
                getWeatherInfo(cityName = _state.value.searchInput)
                _state.update { newState ->
                    newState.copy(
                        isCityView = true
                    )
                }
            }
        }
    }

    private fun getWeatherInfo(cityName: String, stateCode: String? = null, countryCode: String? = null) {
        viewModelScope.launch {
            getCityInfo(cityName, stateCode, countryCode)
            val cityInfo = _state.value.cityInfo
            println("City info - $cityInfo")
            getCityWeather(cityInfo!!.lat, cityInfo.lon)
            val cityWeather = _state.value.weatherInfo
            println("City weather info - $cityWeather")
        }
    }

    private suspend fun getCityInfo(
        cityName: String,
        stateCode: String? = null,
        countryCode: String? = null
    ) {
        val query = listOfNotNull(cityName, stateCode, countryCode).joinToString(",")
        try {
            val cityInfoResponse = apiService.getCityInfo(query)
            if (cityInfoResponse.isNotEmpty()) {
                _state.update { newState ->
                    newState.copy(
                        cityInfo = cityInfoResponse[0]
                    )
                }
            }
        } catch (e: Exception) {
            println("Error fetching city info = $e, query - $query")
        }
    }

    private suspend fun getCityWeather(latitude: Double, longitude: Double) {
        try {
            val cityWeatherResponse = apiService.getCityWeather(latitude, longitude)
            _state.update { newState ->
                newState.copy(
                    weatherInfo = cityWeatherResponse
                )
            }
        } catch (e: Exception) {
            println("Error fetching city weather info = $e")
        }
    }
}