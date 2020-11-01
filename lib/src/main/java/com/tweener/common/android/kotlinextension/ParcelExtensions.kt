package com.tweener.common.android.kotlinextension

import android.os.Parcel
import java.util.*

/**
 * @author Vivien Mahe
 * @since 01/10/2020
 */
inline fun <T> Parcel.readNullable(reader: () -> T) = if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }
}

fun Parcel.readBool() = readInt() != 0

fun Parcel.writeBool(value: Boolean) = writeInt(if (value) 1 else 0)

inline fun <reified T : Enum<T>> Parcel.readEnum() = readString()?.let { enumValueOf<T>(it) }

fun <T : Enum<T>> Parcel.writeEnum(value: T?) = writeString(value?.name)

fun Parcel.readDate() = readNullable { Date(readLong()) }

fun Parcel.writeDate(value: Date?) = writeNullable(value) { writeLong(it.time) }