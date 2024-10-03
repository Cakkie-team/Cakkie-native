package com.cakkie.ui.screens.browser

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var isLoading by remember { mutableStateOf(true) }
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
                        isLoading = true
                        backEnabled = view.canGoBack()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        isLoading = false
                    }

                    // Override URL loading to detect unsupported schemes
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString() ?: return false

                        // Handle unsupported schemes (non-http or non-https links)
                        return if (isUnsupportedScheme(url)) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                            true // We've handled the URL ourselves
                        } else {
                            false // Let the WebView handle it
                        }
                    }

                    // Helper function to determine if a URL uses an unsupported scheme
                    private fun isUnsupportedScheme(url: String): Boolean {
                        val uri = Uri.parse(url)
                        val scheme = uri.scheme
                        return scheme != "http" && scheme != "https"
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
                webView = this
            }
        },
        update = {
            if (it.url?.contains("https://cakkie.com?verify=true") == true) {
                onComplete.navigateBack(result = true)
            }
            if (it.url == "https://cakkie.com") {
                onComplete.navigateBack(result = true)
            }
            webView = it
        })

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize(0.6f))
    }
}
