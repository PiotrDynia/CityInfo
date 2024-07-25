package com.example.cityinfo

sealed interface CityInfoAction {
    data class updateSearchInput(val input: String): CityInfoAction
    data object SearchCityInfo: CityInfoAction
}