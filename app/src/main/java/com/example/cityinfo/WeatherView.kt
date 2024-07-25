package com.example.cityinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun WeatherView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "City name: London")
            Text(text = "Weather: cloudy")
            Text(text = "Country: England")
        }
        Column {
            Icon(
                painter = painterResource(id = R.drawable.weather),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Text(text = "Weather: cloudy")
        }
        
    }
}