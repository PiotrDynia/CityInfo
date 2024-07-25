package com.example.cityinfo

import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    cityName: String,
    modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            android.webkit.WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl("https://en.m.wikipedia.org/wiki/$cityName")
        },
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun WebViewPreview() {
    WebView(cityName = "London")
}