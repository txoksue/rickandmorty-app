package com.paradigma.rickyandmorty.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickyandmorty.common.extensions.launchIdling
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
import kotlinx.coroutines.GlobalScope
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

    fun getCharacterLocation() {

        GlobalScope.launchIdling {

            character?.let { character ->

                val result = locationRepository.getLocation(character.locationId)

                when (result) {
                    is Success -> {
                        characterLocation = result.data

                        checkIsFavorite(character.id)

                        _statusScreen.postValue(ScreenState.Results(result.data))
                    }
                    is NoData -> {
                        _statusScreen.postValue(ScreenState.NoData)
                    }
                    is Error -> {
                        _statusScreen.postValue(ScreenState.Error)
                    }
                }

            } ?: kotlin.run { _statusScreen.postValue(ScreenState.NoData) }
        }
    }

    fun checkIsFavorite(characterId: Int) {
        GlobalScope.launchIdling {
            val result = favoriteRepository.getAllFavoriteCharacters()

            if (result is ResultFavorites.Success) {
                _showFavorite.postValue(result.data.any { it.id == characterId })
            }
        }
    }


    fun saveCharacterAsFavorite() {
        GlobalScope.launchIdling {
            character?.let {
                favoriteRepository.saveCharacter(it)
                _showFavorite.postValue(true)
            }
        }
    }

    fun removeCharacterAsFavorite() {
        GlobalScope.launchIdling {
            character?.let {
                favoriteRepository.deleteCharacter(it)
                _showFavorite.postValue(false)
            }
        }
    }
}


