package com.paradigma.rickyandmorty.util

import com.paradigma.rickyandmorty.FakeCharacterRepository
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository


fun CharacterRepository.loadCharactersData() {
    (this@loadCharactersData as FakeCharacterRepository).setData(null)
}


fun CharacterRepository.setCharacterError(value: Boolean) {
    (this@setCharacterError as FakeCharacterRepository).setReturnError(value)
}

fun CharacterRepository.clearCharacters() {
    (this@clearCharacters as FakeCharacterRepository).clearData()
}


fun CharacterRepository.getCharactersData() = (this@getCharactersData as FakeCharacterRepository).getData().toList()





