package com.tweener.common.android.kotlinextension

import android.view.View

/**
 * @author Vivien Mahe
 * @since 04/10/2020
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}