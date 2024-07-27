package com.example.cityinfo.state

sealed interface CityInfoAction {
    data class UpdateSearchInput(val input: String): CityInfoAction
    data object SearchCityInfo: CityInfoAction
    data object SearchCityWeather: CityInfoAction
    data object BackToHome: CityInfoAction
}