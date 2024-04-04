package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.local.LocalProviderHeroes
import com.jufarangoma.marvelcompose.data.models.HeroResponse
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.exceptions.DomainException
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HeroesRepositoryTest {

    private val marvelApi = mockk<MarvelApi>()
    private val localProviderHeroes = mockk<LocalProviderHeroes>()
    private val domainExceptionStrategy = mockk<DomainExceptionStrategy>()
    private lateinit var heroesRepository: HeroesRepository

    @Before
    fun setUp() {
        heroesRepository = HeroesRepositoryImpl(
            marvelApi,
            localProviderHeroes,
            domainExceptionStrategy
        )
    }

    @After
    fun tearDown() {
        confirmVerified(marvelApi, localProviderHeroes, domainExceptionStrategy)
    }

    @Test
    fun getLocalHeroes() = runBlocking {
        every { localProviderHeroes.getHeroes() } returns listOf()

        heroesRepository.getHeroes(null).collect {
            assert(it.isSuccess)
        }

        verify { localProviderHeroes.getHeroes() }
    }

    @Test
    fun getRemoteHeroes() = runBlocking {
        val heroResponse = mockk<HeroResponse>(relaxed = true)
        coEvery { marvelApi.getHeroes("Spiderman") } returns heroResponse

        heroesRepository.getHeroes("Spiderman").collect {
            assert(it.isSuccess)
        }

        coVerify { marvelApi.getHeroes(any()) }
    }

    @Test
    fun getHeroesFailure() = runBlocking {

        val exception = Throwable("Fallo")

        coEvery { marvelApi.getHeroes("Spiderman") } throws exception
        every { domainExceptionStrategy.manageError(exception) } returns DomainException()

        heroesRepository.getHeroes("Spiderman").collect {
            assert(it.isFailure)
        }

        coVerify { marvelApi.getHeroes(any()) }
        verify { domainExceptionStrategy.manageError(any()) }
    }
}