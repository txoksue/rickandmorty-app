package com.paradigma.rickyandmorty.data.mapper


interface Mapper<I, O> {
    fun mapToDomain(input: I): O
    fun mapFromDomain(input: O): I
}
