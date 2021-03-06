package com.paradigma.rickandmorty.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paradigma.rickandmorty.databinding.ComponentNoResultsBinding

class ComponentNoResults : ConstraintLayout {

    private lateinit var binding: ComponentNoResultsBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        binding = ComponentNoResultsBinding.inflate(layoutInflater, this, true)
    }

    fun setError(messageError: String){
        binding.textViewNoResults.text = messageError
    }

    fun setImage(imageId: Int){
        binding.imageViewNoResults.setImageResource(imageId)
    }
}