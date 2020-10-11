package com.tweener.common.android.resourceprovider

import androidx.annotation.StringRes

/**
 * @author Vivien Mahe
 * @since 11/10/2020
 */
interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getStringFormat(@StringRes resId: Int, vararg args: Any?): String

}