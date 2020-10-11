package com.tweener.common.android.kotlinextension

import java.util.*

/**
 * @author Vivien Mahe
 * @since 11/10/2020
 */

/**
 * Resets this calendar to midnight of its current date, setting the hour, minutes, seconds and milliseconds to 0.
 */
fun Calendar.resetDateToMidnight() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}

/**
 * Resets this calendar to midnight one day after its current date, setting the hour, minutes, seconds and milliseconds to 0.
 */
fun Calendar.resetDateToTomorrowMidnight() {
    this.add(Calendar.DAY_OF_YEAR, 1)
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}