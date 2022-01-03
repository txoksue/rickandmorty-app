package com.paradigma.rickyandmorty.util

import com.paradigma.rickyandmorty.FakeFavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.domain.Character


fun FavoritesRepository.setFavorites(favorites: List<Character>?) {
    (this@setFavorites as FakeFavoritesRepository).setData(favorites)
}

fun FavoritesRepository.setFavoritesError(value: Boolean) {
    (this@setFavoritesError as FakeFavoritesRepository).setReturnError(value)
}

fun FavoritesRepository.clearFavorites() {
    (this@clearFavorites as FakeFavoritesRepository).clearData()
}

fun FavoritesRepository.getFavoritesData() = (this@getFavoritesData as FakeFavoritesRepository).getData()

