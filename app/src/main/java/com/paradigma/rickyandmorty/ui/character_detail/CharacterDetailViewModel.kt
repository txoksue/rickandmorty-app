package com.paradigma.rickyandmorty.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickyandmorty.data.repository.ResultLocation.Success
import com.paradigma.rickyandmorty.data.repository.ResultLocation.NoData
import com.paradigma.rickyandmorty.data.repository.ResultLocation.Error
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.ResultFavorites
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val locationRepository: LocationRepository, private val favoriteRepository: FavoritesRepository) : ViewModel() {

    var character: Character? = null
    var characterLocation: Location? = null

    private val _statusScreen = MutableLiveData<ScreenState<Location>>()
    val statusScreen: LiveData<ScreenState<Location>>
        get() = _statusScreen

    private val _showFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val showFavorite: LiveData<Boolean>
        get() = _showFavorite

    fun getCharacterLocation() = viewModelScope.launch {

        character?.let { character ->

            val result = locationRepository.getLocation(character.locationId)

            when (result) {
                is Success -> {
                    characterLocation = result.data

                    checkIsFavorite(character.id)

                    _statusScreen.value = ScreenState.Results(result.data)
                }
                is NoData -> {
                    _statusScreen.value = ScreenState.NoData
                }
                is Error -> {
                    _statusScreen.value = ScreenState.Error
                }
            }

        } ?: kotlin.run { _statusScreen.value = ScreenState.NoData }
    }

    fun checkIsFavorite(characterId: Int) {
        viewModelScope.launch {
            val result = favoriteRepository.getAllFavoriteCharacters()

            if (result is ResultFavorites.Success) {
                _showFavorite.value = result.data.any { it.id == characterId }
            }
        }
    }


    fun saveCharacterAsFavorite() {
        viewModelScope.launch {
            character?.let {
                favoriteRepository.saveCharacter(it)
                _showFavorite.value = true
            }
        }
    }

    fun removeCharacterAsFavorite() {
        viewModelScope.launch{
            character?.let {
                favoriteRepository.deleteCharacter(it)
                _showFavorite.value = false
            }
        }
    }
}
