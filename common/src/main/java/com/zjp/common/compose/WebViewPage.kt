package com.zjp.common.compose

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.util.Base64
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun WebViewPage(url: String, title: String, goBack: () -> Unit = {}) {
    val context = LocalContext.current

    var alterDialogShow by remember {
        mutableStateOf(false)
    }
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

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            val uri = Uri.parse(url)
            if (uri.scheme.equals("https") || uri.scheme.equals("http")) {
                view?.loadUrl(url!!)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }

                if (context != null && intent.resolveActivity(context!!.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    alterDialogShow = true
                }

            }
            return true
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
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.setSupportZoom(true)
                settings.displayZoomControls = false
                settings.builtInZoomControls = true
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                settings.allowFileAccess = true
                settings.loadsImagesAutomatically = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                loadUrl(urlStr)
            }
        }

        AndroidView(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .navigationBarsPadding(),
            factory = {
                webView
            })

        BackHandler(enabled = true) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                goBack()
            }
        }
        if (alterDialogShow) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    alterDialogShow = false
                },
                text = {
                    Text(
                        "没有安装相关应用"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            alterDialogShow = false
                        }
                    ) {
                        Text("OK")
                    }
                },
            )
        }
    }
}
