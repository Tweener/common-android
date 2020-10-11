package com.tweener.common.android.kotlinextension

import com.tweener.common.android.R
import com.tweener.common.android.resourceprovider.ResourceProvider
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Vivien Mahe
 * @since 11/10/2020
 */

/**
 * Formats this date to the given [formatPattern], [locale] and [timeZone] if set. Returns empty string if we couldn't format it.
 */
fun Date?.format(formatPattern: String, locale: Locale, timeZone: TimeZone? = null): String {
    return this?.let {
        return try {
            val simpleDateFormat = SimpleDateFormat(formatPattern, locale)
            timeZone?.let { simpleDateFormat.timeZone = timeZone }
            simpleDateFormat.format(it)
        } catch (throwable: Throwable) {
            String.empty()
        }
    } ?: run {
        return String.empty()
    }
}

/**
 * Whether or not this date is today, based on the given [locale].
 */
fun Date.isToday(locale: Locale = Locale.getDefault()) = this.isSameDay(Date(), locale)

/**
 * Whether or not this date is today, based on the given [timeZone].
 */
fun Date.isToday(timeZone: TimeZone = TimeZone.getDefault()) = this.isSameDay(Date(), timeZone)

/**
 * Whether or not this date was yesterday, based on the given [locale].
 */
fun Date.isYesterday(locale: Locale = Locale.getDefault()) = this.isYesterday(Date(), locale)

/**
 * Whether or not this date is tomorrow, based on the given [locale].
 */
fun Date.isTomorrow(locale: Locale = Locale.getDefault()) = this.isTomorrow(Date(), locale)

/**
 * Returns either "Today" if this date is today, "Yesterday" if the date is yesterday, or format this date using the given [pattern], based on the given [locale] and [timeZone] if set.
 */
fun Date?.formatAsDateOrPattern(pattern: String, resourceProvider: ResourceProvider, locale: Locale, timeZone: TimeZone? = null): String {
    return this?.let {
        return when {
            isToday(locale) -> resourceProvider.getString(R.string.dateToday)
            isYesterday(locale) -> resourceProvider.getString(R.string.dateYesterday)
            isTomorrow(locale) -> resourceProvider.getString(R.string.dateTomorrow)
            else -> format(pattern, locale, timeZone)
        }
    } ?: run {
        return String.empty()
    }
}

/**
 * Whether or not this date is the same date as the given [otherDate], based on the given [locale].
 */
fun Date.isSameDay(otherDate: Date, locale: Locale = Locale.getDefault()): Boolean {
    val calendarThis = Calendar.getInstance(locale).apply {
        time = this@isSameDay
        resetDateToMidnight()
    }

    val calendarOtherDate = Calendar.getInstance(locale).apply {
        time = otherDate
        resetDateToMidnight()
    }

    return TimeUnit.MILLISECONDS.toDays(calendarOtherDate.timeInMillis - calendarThis.timeInMillis).toInt() == 0
}

/**
 * Whether or not this date is the same date as the given [otherDate], based on the given [timeZone].
 */
fun Date.isSameDay(otherDate: Date, timeZone: TimeZone = TimeZone.getDefault()): Boolean {
    val calendarThis = Calendar.getInstance(timeZone).apply {
        time = this@isSameDay
        resetDateToMidnight()
    }

    val calendarOtherDate = Calendar.getInstance(timeZone).apply {
        time = otherDate
        resetDateToMidnight()
    }

    return TimeUnit.MILLISECONDS.toDays(calendarOtherDate.timeInMillis - calendarThis.timeInMillis).toInt() == 0
}

/**
 * Whether or not this date is the day before the given [otherDate], based on the given [locale].
 */
fun Date.isYesterday(otherDate: Date, locale: Locale = Locale.getDefault()): Boolean {
    val calendarThis = Calendar.getInstance(locale).apply {
        time = this@isYesterday
        resetDateToMidnight()
    }

    val calendarOtherDate = Calendar.getInstance(locale).apply {
        time = otherDate
        resetDateToMidnight()
    }

    return TimeUnit.MILLISECONDS.toDays(calendarOtherDate.timeInMillis - calendarThis.timeInMillis).toInt() == 1
}

/**
 * Whether or not this date is the day after the given [otherDate], based on the given [locale].
 */
fun Date.isTomorrow(otherDate: Date, locale: Locale = Locale.getDefault()): Boolean {
    val calendarThis = Calendar.getInstance(locale).apply {
        time = this@isTomorrow
        resetDateToMidnight()
    }

    val calendarOtherDate = Calendar.getInstance(locale).apply {
        time = otherDate
        resetDateToMidnight()
    }

    return TimeUnit.MILLISECONDS.toDays(calendarOtherDate.timeInMillis - calendarThis.timeInMillis).toInt() == -1
}

/**
 * Resets this date to midnight, setting the hour, minutes, seconds and milliseconds to 0.
 */
fun Date.resetDateToMidnight(): Date {
    Calendar.getInstance().apply {
        time = this@resetDateToMidnight
        resetDateToMidnight()
        return time
    }
}

/**
 * Resets this date to midnight of the next day, setting the hour, minutes, seconds and milliseconds to 0.
 */
fun Date.resetDateToTomorrowMidnight(): Date {
    Calendar.getInstance().apply {
        time = this@resetDateToTomorrowMidnight
        resetDateToTomorrowMidnight()
        return time
    }
}