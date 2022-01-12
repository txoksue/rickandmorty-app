package com.paradigma.rickandmorty.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickandmorty.common.extensions.launchIdling
import com.paradigma.rickandmorty.data.repository.ResultLocation.Success
import com.paradigma.rickandmorty.data.repository.ResultLocation.NoData
import com.paradigma.rickandmorty.data.repository.ResultLocation.Error
import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickandmorty.data.repository.local.favorites.ResultFavorites
import com.paradigma.rickandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickandmorty.ui.ScreenState
import com.paradigma.rickandmorty.domain.Location
import com.paradigma.rickandmorty.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getCharacterLocation() {

        viewModelScope.launchIdling {

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
    }

    fun checkIsFavorite(characterId: Int) {
        viewModelScope.launchIdling {
            val result = favoriteRepository.getAllFavoriteCharacters()

            if (result is ResultFavorites.Success) {
                _showFavorite.value = result.data.any { it.id == characterId }
            }
        }
    }


    fun saveCharacterAsFavorite() {
        viewModelScope.launchIdling {
            character?.let {
                favoriteRepository.saveCharacter(it)
                _showFavorite.value = true
            }
        }
    }

    fun removeCharacterAsFavorite() {
        viewModelScope.launchIdling {
            character?.let {
                favoriteRepository.deleteCharacter(it)
                _showFavorite.value = false
            }
        }
    }
}


