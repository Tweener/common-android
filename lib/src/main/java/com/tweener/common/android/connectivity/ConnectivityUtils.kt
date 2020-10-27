package com.tweener.common.android.connectivity

import java.net.InetSocketAddress
import java.net.Socket

/**
 * @author Vivien Mahe
 * @since 27/10/2020
 */
class ConnectivityUtils {

    companion object {

        /**
         * Whether or not the device has an actual access to Internet.
         */
        fun isInternetAvailable(): Boolean =
            try {
                val sockaddr = InetSocketAddress("8.8.8.8", 53)
                val timeout = 1500 // In millisecond

                Socket().apply {
                    connect(sockaddr, timeout)
                    close()
                }

                true
            } catch (throwable: Throwable) {
                false
            }
    }
}