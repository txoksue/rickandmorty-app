package com.paradigma.rickyandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradigma.rickyandmorty.common.extensions.launchIdling
import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.data.repository.ResultCharacters.*
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(var characterRepository: CharacterRepository) : ViewModel() {

    private val _statusScreen = MutableLiveData<ScreenState<List<Character?>>>()
    val statusScreen: LiveData<ScreenState<List<Character?>>>
        get() = _statusScreen

    enum class ScrollState { FIRST_PAGE, NEXT_PAGE, LAST_PAGE }

    var requestingMoreData = false
    private var statusScroll = ScrollState.FIRST_PAGE
    private val characterList = ArrayList<Character?>()
    private var page = 1

    init {
        getCharacters()
    }

    fun getCharacters() {

        viewModelScope.launchIdling {

            val result: ResultCharacters = characterRepository.getCharacters(page)

            when (result) {
                is Success -> {

                    if (result.totalPages == page) statusScroll = ScrollState.LAST_PAGE

                    when (statusScroll) {
                        ScrollState.FIRST_PAGE -> {
                            characterList.clear()
                            characterList.addAll(result.data)
                            statusScroll = ScrollState.NEXT_PAGE
                        }
                        ScrollState.NEXT_PAGE -> {
                            characterList.addAll(result.data)
                        }
                        ScrollState.LAST_PAGE -> {
                            characterList.removeAll { it == null }
                        }
                    }

                    page++
                    requestingMoreData = false

                    _statusScreen.value  = ScreenState.Results(characterList)
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

    fun requestMoreCharacters() {
        requestingMoreData = true
        getCharacters()
    }

    fun requestNewCharacterList() {
        page = 1
        statusScroll = ScrollState.FIRST_PAGE
        _statusScreen.value = ScreenState.Loading
        characterList.clear()
        getCharacters()
    }

    fun isLastPage(): Boolean {
        return statusScroll == ScrollState.LAST_PAGE
    }

}