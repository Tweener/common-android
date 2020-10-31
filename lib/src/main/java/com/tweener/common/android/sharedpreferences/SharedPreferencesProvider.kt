package com.tweener.common.android.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Vivien Mahe
 * @since 31/10/2020
 */
@Singleton
class SharedPreferencesProvider @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    interface OnKeyChangeListener {
        fun onKeyChanged()
    }

    private val keys = mutableSetOf<String>()
    private val listeners = mutableMapOf<String, MutableSet<OnKeyChangeListener>>() // key, list of listeners

    /**
     * Observe changes on any keys in the [SharedPreferences]. Every time a key changes, this listener will get called.
     */
    private val sharedPrefChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        // For each listener in the list, try to find a mapping for the key that has just changed
        listeners
            .filterKeys { it == key }
            .forEach { mapEntry ->
                // We found a mapping between the changed key and a list of listeners, let's fire changes for each listener
                mapEntry.value.forEach { onKeyChangeListener -> onKeyChangeListener.onKeyChanged() }
            }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPrefChangeListener)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, value: T): T {
        keys.add(key)

        return when (value) {
            is String -> sharedPreferences.getString(key, value) as T
            is Int -> sharedPreferences.getInt(key, value) as T
            is Boolean -> sharedPreferences.getBoolean(key, value) as T
            is Long -> sharedPreferences.getLong(key, value) as T
            is Float -> sharedPreferences.getFloat(key, value) as T
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    fun <T> set(key: String, value: T?) {
        keys.add(key)

        when (value) {
            is String -> sharedPreferences.edit { putString(key, value) }
            is Int -> sharedPreferences.edit { putInt(key, value) }
            is Boolean -> sharedPreferences.edit { putBoolean(key, value) }
            is Long -> sharedPreferences.edit { putLong(key, value) }
            is Float -> sharedPreferences.edit { putFloat(key, value) }
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    /**
     * Removes the given [key] from the [SharedPreferences].
     */
    fun remove(key: String) {
        // Remove the key from the list
        keys.remove(key)

        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Clears all the known keys from the [SharedPreferences].
     *
     * NB: We can't use [SharedPreferences.Editor.clear] because it does not fire the [SharedPreferences.OnSharedPreferenceChangeListener] when clearing the Editor.
     */
    fun clear() {
        keys.forEach { key -> sharedPreferences.edit().remove(key).apply() }
        keys.clear()
    }

    /**
     * Registers a new [OnKeyChangeListener] for the given [key] so when this key changes in the [SharedPreferences], the given [action] will be executed.
     */
    fun registerOnSharedPreferenceChangeListener(key: String, action: () -> Unit) {
        // Create a new listener that will fire the given action when the given key is changed
        val listener = object : OnKeyChangeListener {
            override fun onKeyChanged() {
                action()
            }
        }

        listeners[key]
            ?.add(listener) // The key already exists in the listeners list, let's just add this listener to the list
            ?: run {
                // The key does not exist yet in the listeners list, let's create the key and map the list to it
                listeners[key] = mutableSetOf<OnKeyChangeListener>(listener)
            }
    }
}

/**
 * Wrapper of [SharedPreferencesProvider] to allow this [Observable] to get the desired value of the given [key] from the [SharedPreferences].
 */
fun <T> Observable<SharedPreferencesProvider>.get(key: String, defaultValue: T?): Observable<T> =
    map { it.get(key, defaultValue) }

/**
 * Wrapper of [SharedPreferencesProvider] to allow this [Observable] to set the desired value of the given [key] into the [SharedPreferences].
 */
fun <T> Observable<SharedPreferencesProvider>.edit(key: String, value: T?): Completable =
    firstOrError()
        .flatMapCompletable {
            Completable.fromAction {
                it.set(key, value)
            }
        }

/**
 * Wrapper of [SharedPreferencesProvider] to allow this [Observable] to remove the desired value of the given [key] from the [SharedPreferences].
 */
fun Observable<SharedPreferencesProvider>.remove(key: String): Completable =
    firstOrError()
        .flatMapCompletable {
            Completable.fromAction {
                it.remove(key)
            }
        }
