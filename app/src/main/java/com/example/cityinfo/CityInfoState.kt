package com.example.cityinfo

data class CityInfoState(
    val searchInput: String = "",
    val currentScreen: CurrentScreen = CurrentScreen.HOME_SCREEN,
    val cityInfo: CityInfo? = null,
    val weatherInfo: CityWeather? = null,
    val errorMessage: String = ""
)
