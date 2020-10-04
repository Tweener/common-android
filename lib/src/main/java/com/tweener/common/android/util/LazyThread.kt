package com.tweener.common.android.util

/**
 * @author Vivien Mahe
 * @since 04/10/2020
 */

fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)