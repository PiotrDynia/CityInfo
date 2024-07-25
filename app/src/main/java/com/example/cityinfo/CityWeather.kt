package com.example.cityinfo

data class CityWeather(
    val coord: CityCoord,
    val mainWeatherInfo: MainWeatherInfo,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>
)