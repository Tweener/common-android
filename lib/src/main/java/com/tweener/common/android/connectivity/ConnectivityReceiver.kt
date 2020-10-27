package com.tweener.common.android.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tweener.common.android.kotlinextension.setConnectivityStatusChangedListener
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * @author Vivien Mahe
 * @since 2020-10-27
 */
class ConnectivityReceiver : BroadcastReceiver() {

    // region Observable properties

    private val onConnectivityChanged = BehaviorSubject.create<Boolean>() // Is connectivity available
    fun getConnectivityStatus(): Observable<Boolean> = onConnectivityChanged

    // endregion

    override fun onReceive(context: Context, intent: Intent) = context.setConnectivityStatusChangedListener { available -> onConnectivityChanged.onNext(available) }
}