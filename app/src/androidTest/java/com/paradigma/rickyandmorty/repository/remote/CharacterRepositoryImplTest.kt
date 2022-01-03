package com.paradigma.rickyandmorty.repository.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.paradigma.rickyandmorty.FakeRickyAndMortyApiService
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickyandmorty.domain.Character
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest{

    @Inject
    lateinit var characterRepository: CharacterRepositoryImpl

    @Inject
    lateinit var rickyAndMortyApiService: RickyAndMortyApiService

    @Inject
    lateinit var characterMapper: Mapper<CharacterDTO, Character>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
        (rickyAndMortyApiService as FakeRickyAndMortyApiService).setCharacterData()
    }


    @Test
    fun characterRepositoryImpl_getCharacters_page() = runBlocking {
        val result = characterRepository.getCharacters(1)

        val characterList = (rickyAndMortyApiService as FakeRickyAndMortyApiService).getCharacterData()?.map { characterDto -> characterMapper.mapToDomain(characterDto) }

        assertThat(result is ResultCharacters.Success)
        result as ResultCharacters.Success
        assertThat(result.data).isEqualTo(characterList)
    }
}