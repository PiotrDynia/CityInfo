package com.example.cityinfo

data class CityInfoState(
    val searchInput: String = "",
    val isCityView: Boolean = false,
    val cityInfo: CityInfo? = null,
    val weatherInfo: CityWeather? = null
)
