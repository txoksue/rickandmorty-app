package com.paradigma.rickyandmorty.ui.character_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.common.extensions.loadImage
import com.paradigma.rickyandmorty.databinding.FragmentCharacterDetailBinding
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.ui.ScreenState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.character = args.character

        viewModel.getCharacterLocation()

        binding.imageViewFavorite.setOnClickListener {
            when(viewModel.showFavorite.value){
                true -> {
                    viewModel.removeCharacterAsFavorite()
                }
                else -> {
                    viewModel.saveCharacterAsFavorite()
                }
            }
        }

        initObservers()
    }

    private fun initObservers() {

        viewModel.statusScreen.observe(viewLifecycleOwner, Observer { statusScreen ->

            when (statusScreen) {
                is ScreenState.Results<Location> -> {

                    with(binding) {

                        imageViewPhoto.loadImage(args.character.image)

                        val animZoomIn = AnimationUtils.loadAnimation(
                            context,
                            R.anim.intro_animation
                        )
                        binding.imageViewPhoto.startAnimation(animZoomIn)

                        textViewName.text = if (statusScreen.data.name.isNotEmpty()) statusScreen.data.name else context?.getString(R.string.no_name)
                        textViewType.text = if (statusScreen.data.type.isNotEmpty()) statusScreen.data.type else context?.getString(R.string.no_type_available)
                        textViewDimension.text = if (statusScreen.data.dimension.isNotEmpty()) statusScreen.data.dimension else context?.getString(R.string.no_dimension_available)

                    }
                }
                is ScreenState.Error -> {

                }
                is ScreenState.Loading -> {

                }
                else -> {

                }
            }
        })

        viewModel.showFavorite.observe(viewLifecycleOwner, Observer { favorite ->
            toggleFavoriteImage(favorite)
        })
    }


    private fun toggleFavoriteImage(favorite: Boolean){
        when (favorite){
            true -> {
                binding.imageViewFavorite.setImageResource(R.drawable.ic_favorite_star)
            }
            else -> {
                binding.imageViewFavorite.setImageResource(R.drawable.ic_no_favorite_star)
            }
        }
    }
}