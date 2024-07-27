package com.example.cityinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cityinfo.screens.CurrentScreen
import com.example.cityinfo.screens.ErrorScreen
import com.example.cityinfo.screens.HomeScreen
import com.example.cityinfo.screens.InfoScreen
import com.example.cityinfo.screens.WeatherView
import com.example.cityinfo.state.CityInfoAction
import com.example.cityinfo.state.CityInfoViewModel
import com.example.cityinfo.ui.theme.CityInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CityInfoTheme {
                val viewModel by viewModels<CityInfoViewModel>()
                val state by viewModel.state.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    BackHandler {
                        viewModel.onAction(CityInfoAction.BackToHome)
                    }
                    when (state.currentScreen) {
                        CurrentScreen.HOME_SCREEN -> {
                            HomeScreen(
                                state = state,
                                onAction = viewModel::onAction
                            )
                        }

                        CurrentScreen.CITY_INFO_SCREEN -> {
                            InfoScreen(cityName = state.searchInput)
                        }

                        CurrentScreen.CITY_WEATHER_SCREEN -> {
                            WeatherView(state = state)
                        }

                        CurrentScreen.ERROR_SCREEN -> {
                            ErrorScreen(errorMessage = state.errorMessage,
                                onRetry = {
                                    viewModel.onAction(CityInfoAction.BackToHome)
                                })
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CityInfoHomeScreenPreview() {
    CityInfoTheme {

    }
}