package com.example.cityinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
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
                    if (state.isCityView) {
                        CityView(
                            cityName = state.searchInput
                        )
                    } else {
                        HomeScreen(
                            state = state,
                            onAction = viewModel::onAction
                        )
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