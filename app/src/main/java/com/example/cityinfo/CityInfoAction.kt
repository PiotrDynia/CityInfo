package com.example.cityinfo

sealed interface CityInfoAction {
    data class UpdateSearchInput(val input: String): CityInfoAction
    data object SearchCityInfo: CityInfoAction
    data object SearchCityWeather: CityInfoAction
    data object OnBackPressed: CityInfoAction
}