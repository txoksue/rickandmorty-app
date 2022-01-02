package com.paradigma.rickyandmorty.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickyandmorty.data.repository.ResultLocation.Success
import com.paradigma.rickyandmorty.data.repository.ResultLocation.NoData
import com.paradigma.rickyandmorty.data.repository.ResultLocation.Error
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val locationRepository: LocationRepository) : ViewModel() {

    var character: Character? = null
    var characterLocation: Location? = null


    private val _statusScreen = MutableLiveData<ScreenState<Location>>()
    val statusScreen: LiveData<ScreenState<Location>>
        get() = _statusScreen

    fun getCharacterLocation() = viewModelScope.launch {

        character?.let { character ->

            val result = locationRepository.getLocation(character.locationId)

            when (result) {
                is Success -> {
                    characterLocation = result.data

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
