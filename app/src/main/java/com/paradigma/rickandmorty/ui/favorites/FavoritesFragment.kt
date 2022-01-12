package com.paradigma.rickandmorty.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paradigma.rickandmorty.domain.Character
import androidx.recyclerview.widget.LinearLayoutManager
import com.paradigma.rickandmorty.R
import com.paradigma.rickandmorty.databinding.FavoritesFragmentBinding
import com.paradigma.rickandmorty.ui.ScreenState.*
import com.paradigma.rickandmorty.ui.characters.adapter.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoriteCharacterAdapter : CharactersAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FavoritesFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteCharacterAdapter = CharactersAdapter(arrayListOf(), onClickCharacter = {character ->  navigateToDetail(character)})

        viewModel.getFavorites()

        setupRecyclerViewForFavorites()

        initObservers()
    }


    private fun initObservers(){
        viewModel.statusScreen.observe(viewLifecycleOwner, Observer { favoriteList ->

            when (favoriteList) {
                is Loading -> {
                }
                is Results -> {
                    favoriteCharacterAdapter.setItems(favoriteList.data)

                    with(binding){
                        componentFavoriteNoResult.visibility = View.GONE
                        recyclerViewFavoriteList.visibility = View.VISIBLE
                    }
                }
                is NoData -> {
                    with(binding) {
                        componentFavoriteNoResult.setImage(R.mipmap.no_results_background)
                        componentFavoriteNoResult.setError(getString(R.string.favorites_no_results))
                        componentFavoriteNoResult.visibility = View.VISIBLE
                        recyclerViewFavoriteList.visibility = View.GONE
                    }
                }
                is Error -> {
                    with(binding) {
                        componentFavoriteNoResult.setImage(R.mipmap.no_results_background)
                        componentFavoriteNoResult.setError(getString(R.string.characters_error))
                        componentFavoriteNoResult.visibility = View.VISIBLE
                        recyclerViewFavoriteList.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupRecyclerViewForFavorites() {
        binding.recyclerViewFavoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteCharacterAdapter
            setHasFixedSize(true)
        }
    }


    private fun navigateToDetail(character: Character) {
        val direction = FavoritesFragmentDirections.actionFavoritesFragmentToCharacterDetailFragment(character)
        findNavController().navigate(direction)
    }

}