package com.example.cityinfo.api

import com.example.cityinfo.CityInfo
import com.example.cityinfo.CityWeather
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "cbc23bc08896b9407e28cd94b89007d3"

interface WeatherService {

    @GET("/geo/1.0/direct")
    suspend fun getCityInfo(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = API_KEY
    ): List<CityInfo>

    @GET("/data/2.5/weather")
    suspend fun getCityWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY
    ): CityWeather
}