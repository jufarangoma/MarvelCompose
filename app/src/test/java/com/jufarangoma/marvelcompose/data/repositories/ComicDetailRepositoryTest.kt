package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.models.ComicDTO
import com.jufarangoma.marvelcompose.data.models.ComicResponse
import com.jufarangoma.marvelcompose.data.models.DataResponse
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.exceptions.HttpCatchException
import com.jufarangoma.marvelcompose.domain.repositories.ComicDetailRepository
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

class ComicDetailRepositoryTest {

    private val marvelApi = mockk<MarvelApi>()
    private val domainExceptionStrategy = mockk<DomainExceptionStrategy>()
    private lateinit var comicDetailRepository: ComicDetailRepository

    @Before
    fun setUp() {
        comicDetailRepository = ComicDetailRepositoryImpl(marvelApi, domainExceptionStrategy)
    }

    @Test
    fun getComicDetailSuccessfulButDataIsNull() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)

        coEvery { marvelApi.getComicDetail(1) } returns comicResponse
        every { comicResponse.data } returns null

        comicDetailRepository.getComicDetail(1).collect { result ->
            result.onSuccess {
                assert(it == null)
            }
        }

        coVerify { marvelApi.getComicDetail(1) }
        verify {
            comicResponse.data
        }
        confirmVerified(comicResponse)
    }

    @Test
    fun getComicDetailSuccessfulButResultsIsNull() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)
        val dataResponse = mockk<DataResponse>(relaxed = true)

        coEvery { marvelApi.getComicDetail(1) } returns comicResponse
        every { comicResponse.data } returns dataResponse
        every { dataResponse.results } returns null

        comicDetailRepository.getComicDetail(1).collect { result ->
            result.onSuccess {
                assert(it == null)
            }
        }

        coVerify { marvelApi.getComicDetail(1) }
        verify {
            comicResponse.data
        }
        confirmVerified(comicResponse)
    }
    @Test
    fun getComicDetailSuccessful() = runBlocking {
        val comicResponse = mockk<ComicResponse>(relaxed = true)
        val dataResponse = mockk<DataResponse>(relaxed = true)
        val comicDTO = mockk<ComicDTO>(relaxed = true)

        coEvery { marvelApi.getComicDetail(1) } returns comicResponse
        every { comicResponse.data } returns dataResponse
        every { dataResponse.results } returns listOf(comicDTO)

        comicDetailRepository.getComicDetail(1).collect { result ->
            assert(result.isSuccess)
        }

        coVerify { marvelApi.getComicDetail(1) }
        verify {
            comicResponse.data
            comicDTO.toDomainComic()
        }
        confirmVerified(comicResponse)
    }

    @Test
    fun getComicDetailFailure() = runBlocking {
        val exception = Throwable("Fallo")

        coEvery { marvelApi.getComicDetail(1) } throws exception
        every { domainExceptionStrategy.manageError(exception) } returns HttpCatchException(404)

        comicDetailRepository.getComicDetail(1).collect {
            assert(it.isFailure)
        }

        coVerify { marvelApi.getComicDetail(1) }
        verify {
            domainExceptionStrategy.manageError(exception)
        }
    }

    @After
    fun tearDown(){
        confirmVerified(marvelApi, domainExceptionStrategy)
        unmockkAll()
    }

}