package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.models.ComicDTO
import com.jufarangoma.marvelcompose.data.models.ComicResponse
import com.jufarangoma.marvelcompose.data.models.DataResponse
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.exceptions.HttpCatchException
import com.jufarangoma.marvelcompose.domain.repositories.ComicsRepository
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ComicRepositoryTest {

    private val marvelApi = mockk<MarvelApi>()
    private val domainExceptionStrategy = mockk<DomainExceptionStrategy>()
    private lateinit var comicsRepository: ComicsRepository

    @Before
    fun setUp() {
        comicsRepository = ComicsRepositoryImpl(marvelApi, domainExceptionStrategy)
    }

    @Test
    fun getComicsSuccessfulButDataIsNull() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)

        coEvery { marvelApi.getComics(1) } returns comicResponse
        every { comicResponse.data } returns null

        comicsRepository.getComics(1).collect { result ->
            result.onSuccess {
                assert(it.isEmpty())
            }
        }

        coVerify { marvelApi.getComics(1) }
        verify {
            comicResponse.data
        }
        confirmVerified(comicResponse)
    }

    @Test
    fun getComicsSuccessfulButResultsAreNull() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)
        val dataResponse = mockk<DataResponse>(relaxed = true)

        coEvery { marvelApi.getComics(1) } returns comicResponse
        every { comicResponse.data } returns dataResponse
        every { dataResponse.results } returns null

        comicsRepository.getComics(1).collect { result ->
            result.onSuccess {
                assert(it.isEmpty())
            }
        }

        coVerify { marvelApi.getComics(1) }
        verify {
            comicResponse.data
            dataResponse.results
        }
        confirmVerified(comicResponse, dataResponse)
    }

    @Test
    fun getComicsSuccessful() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)
        val dataResponse = mockk<DataResponse>(relaxed = true)
        val comic1 = mockk<ComicDTO>(relaxed = true)

        coEvery { marvelApi.getComics(1) } returns comicResponse
        every { comicResponse.data } returns dataResponse
        every { dataResponse.results } returns listOf(comic1)

        comicsRepository.getComics(1).collect {
            assert(it.isSuccess)
        }

        coVerify { marvelApi.getComics(1) }
        verify {
            comicResponse.data
            dataResponse.results
            comic1.toDomainComic()
        }
        confirmVerified(comicResponse, dataResponse, comic1)
    }

    @Test
    fun getComicFailure() = runBlocking {
        val exception = Throwable("Fallo")

        coEvery { marvelApi.getComics(1) } throws exception
        every { domainExceptionStrategy.manageError(exception) } returns HttpCatchException(404)

        comicsRepository.getComics(1).collect {
            assert(it.isFailure)
        }

        coVerify { marvelApi.getComics(1) }
        verify {
           domainExceptionStrategy.manageError(exception)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
        confirmVerified(marvelApi, domainExceptionStrategy)
    }
}