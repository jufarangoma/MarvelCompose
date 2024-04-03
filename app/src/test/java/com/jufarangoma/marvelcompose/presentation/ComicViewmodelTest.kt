package com.jufarangoma.marvelcompose.presentation

import com.jufarangoma.marvelcompose.MainDispatcherRule
import com.jufarangoma.marvelcompose.data.repositories.ComicsRepositoryImpl
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ComicVieModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val comicsRepository = mockk<ComicsRepositoryImpl>()
    private val _comicState = mockk<MutableStateFlow<ComicStates>>(relaxed = true)
    private lateinit var comicsViewModel: ComicsViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        comicsViewModel = ComicsViewModel(
            comicsRepository,
            _comicState,
            mainDispatcherRule.getTestDispatcher()
        )
    }

    @Test
    fun testComicsSuccess() = runTest(mainDispatcherRule.getTestDispatcher()) {

        val slot = slot<ComicStates>()
        coEvery { comicsRepository.getComics(1) } returns flowOf(Result.success(listOf()))
        every { _comicState.value = capture(slot) } returns Unit

        comicsViewModel.getComics(1)

        coVerify(exactly = 1) { comicsRepository.getComics(1) }
        assert(slot.captured is ComicStates.Success)
    }

    @Test
    fun testComicsFailure() = runTest(mainDispatcherRule.getTestDispatcher()) {

        val slot = slot<ComicStates>()
        coEvery { comicsRepository.getComics(1) } returns flowOf(Result.failure(Throwable()))
        every { _comicState.value = capture(slot) } returns Unit

        comicsViewModel.getComics(1)

        coVerify(exactly = 1) { comicsRepository.getComics(1) }
        assert(slot.captured is ComicStates.Error)
    }

    @After
    fun tearDown(){
        confirmVerified(comicsRepository)
    }

}