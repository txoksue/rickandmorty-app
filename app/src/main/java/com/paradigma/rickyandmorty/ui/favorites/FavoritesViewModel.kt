package com.paradigma.rickyandmorty.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickyandmorty.common.extensions.launchIdling
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.ResultFavorites.*
import com.paradigma.rickyandmorty.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.paradigma.rickyandmorty.domain.Character
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(var favoritesRepository: FavoritesRepository) : ViewModel() {

    private val _statusScreen = MutableLiveData<ScreenState<List<Character?>>>()
    val statusScreen: LiveData<ScreenState<List<Character?>>>
        get() = _statusScreen

    private val _favorites: MutableLiveData<ArrayList<Character>> = MutableLiveData<ArrayList<Character>>(arrayListOf())
    val favorites: LiveData<ArrayList<Character>>
        get() = _favorites

    fun getFavorites() = GlobalScope.launchIdling {

        val result = favoritesRepository.getAllFavoriteCharacters()

        when (result) {
            is Success -> {
                _statusScreen.postValue(ScreenState.Results(result.data))
            }
            is NoData -> {
                _statusScreen.postValue(ScreenState.NoData)
            }
            is Error -> {
                _statusScreen.postValue(ScreenState.Error)
            }
        }
    }
}