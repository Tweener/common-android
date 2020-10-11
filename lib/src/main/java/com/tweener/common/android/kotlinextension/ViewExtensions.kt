package com.tweener.common.android.kotlinextension

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

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

/**
 * Sets this view to [View.VISIBLE] if the given [show] is true. Sets this view to [View.GONE] otherwise.
 */
fun View.visibleOrGone(show: Boolean) {
    if (show) visible() else gone()
}

/**
 * Sets this view to [View.GONE] if the given [show] is true. Sets this view to [View.VISIBLE] otherwise.
 */
fun View.goneOrVisible(show: Boolean) {
    if (show) gone() else visible()
}

/**
 * Sets this view to [View.VISIBLE] if the given [show] is true. Sets this view to [View.INVISIBLE] otherwise.
 */
fun View.visibleOrInvisible(show: Boolean) {
    if (show) visible() else invisible()
}

/**
 * Sets this view to [View.INVISIBLE] if the given [show] is true. Sets this view to [View.VISIBLE] otherwise.
 */
fun View.invisibleOrVisible(show: Boolean) {
    if (show) invisible() else visible()
}

/**
 * Whether or not this view is visible.
 */
fun View.isVisible() = visibility == View.VISIBLE

/**
 * Shows the soft keyboard once this view gained focus.
 */
fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

/**
 * Hides the soft keyboard.
 */
fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}


/**
 * Forces the soft keyboard to hide.
 */
fun View.forceHideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Executes the given function after the view has been measured.
 */
inline fun View.afterMeasured(crossinline func: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                func()
            }
        }
    })
}