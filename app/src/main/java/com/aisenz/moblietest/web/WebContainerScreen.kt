package com.aisenz.moblietest.web

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebContainerScreen(url: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    val webSettings = settings
                    // 开启JS
                    webSettings.javaScriptEnabled = true
                    webSettings.domStorageEnabled = true
                    webSettings.allowFileAccess = false
                    webSettings.cacheMode = WebSettings.LOAD_DEFAULT
                    webSettings.useWideViewPort = true
                    webSettings.loadWithOverviewMode = true
                    // 禁止缩放（可选）
                    webSettings.setSupportZoom(false)
                    webSettings.builtInZoomControls = false
                    webSettings.displayZoomControls = false
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = { webView ->
                // 当url参数变化时重新加载
                webView.loadUrl(url)
            },
            onRelease = { webView ->
                webView.destroy()
            }

        )
    }
}