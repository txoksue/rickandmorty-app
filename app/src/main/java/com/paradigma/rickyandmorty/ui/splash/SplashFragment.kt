package com.paradigma.rickyandmorty.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        startAnimation()

        return binding.root
    }



    private fun startAnimation(){
        val animZoomIn = AnimationUtils.loadAnimation(context, R.anim.intro_animation)
        binding.imageViewLogo.startAnimation(animZoomIn)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCharactersFragment())
        }, 3000)
    }

}