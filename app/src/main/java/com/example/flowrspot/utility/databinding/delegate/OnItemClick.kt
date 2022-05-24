package com.example.flowrspot.utility.databinding.delegate

import android.view.View

interface OnItemClick<T> {

    fun onItemClick(v: View, t: T)
}