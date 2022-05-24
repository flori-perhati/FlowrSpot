package com.example.flowrspot.utility.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.flowrspot.utility.extension.loadImageFromPath

object CustomBinding {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl"])
    fun loadImageUrl(imageView: ImageView, imageUrl: String) {
        imageView.loadImageFromPath(imageUrl)
    }
}