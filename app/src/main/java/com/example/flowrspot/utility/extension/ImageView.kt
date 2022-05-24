package com.example.flowrspot.utility.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.flowrspot.R

fun ImageView.loadImageFromPath(imageUrl: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.color_cursor)
        .error(R.drawable.color_cursor)

    Glide.with(this.context).load(imageUrl).apply(options).into(this)
}