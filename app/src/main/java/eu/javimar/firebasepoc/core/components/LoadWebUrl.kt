package eu.javimar.firebasepoc.core.components

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun LoadWebUrl(url: String) {
    val context = LocalContext.current
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(webIntent)
}

@Composable
fun LoadWebView(url: String) {
    val context = LocalContext.current
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}