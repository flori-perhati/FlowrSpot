package com.example.flowrspot.utility.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Hides the keyboard.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Shows the keyboard.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}