package com.paradigma.rickyandmorty.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.paradigma.rickyandmorty.common.idling_resource.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        //.placeholder(R.mipmap.image_loader)
        .into(this)
}



fun CoroutineScope.launchIdling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    EspressoIdlingResource.increment()
    val job = this.launch(context, start, block)
    job.invokeOnCompletion { EspressoIdlingResource.decrement() }
    return job
}