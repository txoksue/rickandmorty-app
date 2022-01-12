package com.paradigma.rickandmorty.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paradigma.rickandmorty.databinding.ComponentImageTextBinding

class ComponentImageText: ConstraintLayout {

    lateinit var binding: ComponentImageTextBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(infService) as LayoutInflater
        binding = ComponentImageTextBinding.inflate(layoutInflater, this, true)
    }

    fun setImage(icon: Int){
        binding.imageView.setImageResource(icon)
    }


    fun setText(text: String){
        binding.textViewLabel.text = text
    }

}