package com.paradigma.rickyandmorty.util

import com.paradigma.rickyandmorty.FakeLocationRepository
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository


fun LocationRepository.loadLocationData() {
    (this@loadLocationData as FakeLocationRepository).setData(null)
}


fun LocationRepository.setLocationError(value: Boolean) {
    (this@setLocationError as FakeLocationRepository).setReturnError(value)
}

fun LocationRepository.clearLocation() {
    (this@clearLocation as FakeLocationRepository).clearData()
}


fun LocationRepository.getLocationData() = (this@getLocationData as FakeLocationRepository).getData()





