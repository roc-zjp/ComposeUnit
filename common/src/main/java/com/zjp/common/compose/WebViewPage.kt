package com.zjp.common.compose

import android.net.http.SslError
import android.util.Base64
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewPage(url: String, title: String, goBack: () -> Unit = {}) {
    val webViewChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

    }
    val webViewClient = object : WebViewClient() {
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }
    }
    Scaffold(
        topBar = {
            UnitTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) {
        val urlStr = String(Base64.decode(url, Base64.DEFAULT))
        val canGoBack = remember {
            false
        }

        val context = LocalContext.current
        val webView = remember {
            WebView(context).apply {
                this.webViewClient = webViewClient
                this.webChromeClient = webViewChromeClient
                WebView.setWebContentsDebuggingEnabled(true)
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.javaScriptEnabled = true
                loadUrl(urlStr)
            }
        }

        AndroidView(modifier = Modifier
            .padding(it)
            .fillMaxSize(), factory = {
            webView
        })

        BackHandler(enabled = true) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                goBack()
            }
        }
    }
}
