@file:Suppress("unused", "DEPRECATION")

package ru.gidline.app.extension

import android.net.ConnectivityManager

val ConnectivityManager.isConnected: Boolean
    get() = activeNetworkInfo?.isConnected == true