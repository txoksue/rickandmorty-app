package com.paradigma.rickyandmorty

interface FakeRepository<T> {

    fun setData(data: T?)

    fun setReturnError(value: Boolean)

    fun clearData()

    fun getData(): T
}