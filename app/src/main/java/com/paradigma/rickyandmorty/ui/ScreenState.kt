package com.paradigma.rickyandmorty.ui

import com.paradigma.rickyandmorty.domain.Character

sealed class ScreenState {

    data class Results(val data: ArrayList<Character?>): ScreenState()
    object Loading: ScreenState()
    object Error: ScreenState()
    object NoData: ScreenState()

}