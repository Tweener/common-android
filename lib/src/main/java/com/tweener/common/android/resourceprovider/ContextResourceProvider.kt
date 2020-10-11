package com.tweener.common.android.resourceprovider

import android.content.Context
import androidx.annotation.StringRes

/**
 * @author Vivien Mahe
 * @since 11/10/2020
 */
class ContextResourceProvider(
    private val context: Context
) : ResourceProvider {

    override fun getString(@StringRes resId: Int): String = context.getString(resId)

    override fun getStringFormat(@StringRes resId: Int, vararg args: Any?): String = String.format(getString(resId), *args)
}