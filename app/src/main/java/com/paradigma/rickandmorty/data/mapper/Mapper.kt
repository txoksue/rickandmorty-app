package com.paradigma.rickandmorty.data.mapper


interface Mapper<I, O> {
    fun mapToDomain(input: I): O
    fun mapFromDomain(input: O): I
}
