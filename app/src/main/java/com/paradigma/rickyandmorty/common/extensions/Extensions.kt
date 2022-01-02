package com.paradigma.rickyandmorty.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.paradigma.rickyandmorty.R

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        //.placeholder(R.mipmap.image_loader)
        .into(this)
}