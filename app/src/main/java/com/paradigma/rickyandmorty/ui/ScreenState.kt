package com.paradigma.rickyandmorty.ui

import com.paradigma.rickyandmorty.domain.Character

sealed class ScreenState <out R>{

    data class Results<out T>(val data: T): ScreenState<T>()
    object Loading: ScreenState<Nothing>()
    object Error: ScreenState<Nothing>()
    object NoData: ScreenState<Nothing>()

}