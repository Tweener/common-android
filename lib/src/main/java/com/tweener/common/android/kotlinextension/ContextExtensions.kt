package com.tweener.common.android.kotlinextension

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

/**
 * @author Vivien Mahe
 * @since 27/10/2020
 */

fun Context.setConnectivityStatusChangedListener(consumer: (Boolean) -> Unit) {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            consumer(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            consumer(false)
        }
    })
}