package com.example.cityinfo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CityView(
    cityName: String,
    modifier: Modifier = Modifier) {
    // TODO implement weather view
    WebView(cityName = cityName)
}