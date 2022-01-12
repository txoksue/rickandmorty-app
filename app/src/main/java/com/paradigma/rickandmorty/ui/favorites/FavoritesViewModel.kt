package com.paradigma.rickandmorty.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickandmorty.common.extensions.launchIdling
import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickandmorty.data.repository.local.favorites.ResultFavorites.*
import com.paradigma.rickandmorty.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.paradigma.rickandmorty.domain.Character

@HiltViewModel
class FavoritesViewModel @Inject constructor(var favoritesRepository: FavoritesRepository) : ViewModel() {

    private val _statusScreen = MutableLiveData<ScreenState<List<Character?>>>()
    val statusScreen: LiveData<ScreenState<List<Character?>>>
        get() = _statusScreen

    private val _favorites: MutableLiveData<ArrayList<Character>> = MutableLiveData<ArrayList<Character>>(arrayListOf())
    val favorites: LiveData<ArrayList<Character>>
        get() = _favorites

    fun getFavorites() = viewModelScope.launchIdling {

        val result = favoritesRepository.getAllFavoriteCharacters()

        when (result) {
            is Success -> {
                _statusScreen.value = ScreenState.Results(result.data)
            }
            is NoData -> {
                _statusScreen.value = ScreenState.NoData
            }
            is Error -> {
                _statusScreen.value = ScreenState.Error
            }
        }
    }
}