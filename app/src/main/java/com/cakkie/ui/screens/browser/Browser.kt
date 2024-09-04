package com.cakkie.ui.screens.browser

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Composable
@Destination
fun Browser(
    url: String,
    onComplete: ResultBackNavigator<Boolean>
) {
    var backEnabled by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) } // Add loading state
    var webView: WebView? = null
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        isLoading = true // Set loading state to true when page starts loading
                        backEnabled = view.canGoBack()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        isLoading = false // Set loading state to false when page finishes loading
                    }
                }
                settings.javaScriptEnabled = true
                // Other settings...

                loadUrl(url)
                webView = this
            }
        }, update = {
            webView?.loadUrl(url)
            if (it.url?.contains("https://cakkie.com?verify=true") == true) {
                onComplete.navigateBack(result = true)
            }
            webView = it
        })

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

    if (isLoading) {
        // Show loading indicator while the page is loading
        // You can replace this with your custom loading indicator
        CircularProgressIndicator(modifier = Modifier.fillMaxWidth(0.2f))
    }
}