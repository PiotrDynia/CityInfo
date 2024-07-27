package com.example.cityinfo.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityinfo.api.RetrofitInstance
import com.example.cityinfo.screens.CurrentScreen
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
        when (action) {
            is CityInfoAction.UpdateSearchInput -> {
                _state.update { newState ->
                    newState.copy(
                        searchInput = action.input
                    )
                }
            }

            CityInfoAction.SearchCityInfo -> {
                viewModelScope.launch {
                    if (_state.value.searchInput.isNotEmpty()) {
                        getCityInfo(cityName = _state.value.searchInput)
                        _state.update { newState ->
                            newState.copy(
                                currentScreen = CurrentScreen.CITY_INFO_SCREEN
                            )
                        }
                    }
                }
            }

            CityInfoAction.SearchCityWeather -> {
                if (_state.value.searchInput.isNotEmpty()) {
                    getWeatherInfo(cityName = _state.value.searchInput)
                    _state.update { newState ->
                        newState.copy(
                            currentScreen = CurrentScreen.CITY_WEATHER_SCREEN
                        )
                    }
                }
            }

            CityInfoAction.BackToHome -> {
                _state.update { newState ->
                    newState.copy(
                        currentScreen = CurrentScreen.HOME_SCREEN
                    )
                }
            }
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
                } else {
                    setErrorMessage()
                }
            } catch (e: Exception) {
                setErrorMessage()
            }
    }

    private fun getWeatherInfo(
        cityName: String,
        stateCode: String? = null,
        countryCode: String? = null
    ) {
        viewModelScope.launch {
            getCityInfo(cityName, stateCode, countryCode)
            val cityInfo = _state.value.cityInfo
            if (cityInfo != null) {
                getCityWeather(cityInfo.lat, cityInfo.lon)
            } else {
                setErrorMessage("weather")
            }
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
            setErrorMessage("weather")
        }
    }

    private fun setErrorMessage(errorType: String = "") {
        _state.update { newState ->
            newState.copy(
                errorMessage = "Error fetching $errorType info about the city.",
                currentScreen = CurrentScreen.ERROR_SCREEN
            )
        }
    }
}