package ru.gidline.app.screen.documents.browser

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import ru.gidline.app.BuildConfig
import ru.gidline.app.extension.activityCallback
import ru.gidline.app.extension.isKitkatPlus
import ru.gidline.app.extension.isLollipopPlus
import ru.gidline.app.screen.base.listener.IView

private typealias W = WebView

class WebChrome : WebChromeClient() {

    override fun onProgressChanged(view: W, progress: Int) {
        view.context.activityCallback<IView> {
            when (val topFragment = topFragment) {
                is BrowserContract.View -> {
                    topFragment.onProgress(progress)
                }
            }
        }
    }
}

@Suppress("DEPRECATION")
class WebClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: W, url: String?): Boolean {
        return false
    }
}

class BrowserView : W {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup()
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        setup()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    fun setup() {
        if (isInEditMode) {
            return
        }
        settings.apply {
            useWideViewPort = true
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            setAppCacheEnabled(true)
            setAppCachePath(context.cacheDir.path)
            setAppCacheMaxSize(100 * 1024 * 1024)
        }
        webViewClient = WebClient()
        webChromeClient = WebChrome()
        CookieManager.getInstance().also {
            if (isLollipopPlus()) {
                it.setAcceptThirdPartyCookies(this, true)
            } else {
                it.setAcceptCookie(true)
            }
        }
    }

    override fun hasOverlappingRendering() = false

    companion object {

        init {
            if (isKitkatPlus()) {
                setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
            }
        }
    }
}