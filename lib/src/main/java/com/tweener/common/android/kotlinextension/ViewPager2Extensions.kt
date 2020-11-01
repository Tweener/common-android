package com.tweener.common.android.kotlinextension

import androidx.viewpager2.widget.ViewPager2

/**
 * @author Vivien Mahe
 * @since 01/11/2020
 */

fun ViewPager2.onPageSelected(consumer: (Int) -> Unit = {}) =
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            consumer(position)
        }
    })