package com.jufarangoma.marvelcompose.presentation

import com.jufarangoma.marvelcompose.MainDispatcherRule
import com.jufarangoma.marvelcompose.data.repositories.ComicDetailRepositoryImpl
import com.jufarangoma.marvelcompose.domain.entities.Comic
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicDetailState
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicDetailViewModel
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

class ComicDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val comicDetailRepository = mockk<ComicDetailRepositoryImpl>()
    private val _comicDetailState = mockk<MutableStateFlow<ComicDetailState>>(relaxed = true)
    private lateinit var comicDetailViewModel: ComicDetailViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        comicDetailViewModel = ComicDetailViewModel(
            comicDetailRepository,
            _comicDetailState,
            mainDispatcherRule.getTestDispatcher()
        )
    }

    @Test
    fun testComicsSuccess() = runTest(mainDispatcherRule.getTestDispatcher()) {

        val slot = slot<ComicDetailState>()
        val comic = mockk<Comic>()
        coEvery { comicDetailRepository.getComicDetail(1) } returns flowOf(Result.success(comic))
        every { _comicDetailState.value = capture(slot) } returns Unit

        comicDetailViewModel.getComicDetail(1)

        coVerify(exactly = 1) { comicDetailRepository.getComicDetail(1) }
        assert(slot.captured is ComicDetailState.Success)
    }

    @Test
    fun testComicsFailureWhenComicIsNull() = runTest(mainDispatcherRule.getTestDispatcher()) {

        val slot = slot<ComicDetailState>()
        coEvery { comicDetailRepository.getComicDetail(1) } returns flowOf(Result.success(null))
        every { _comicDetailState.value = capture(slot) } returns Unit

        comicDetailViewModel.getComicDetail(1)

        coVerify(exactly = 1) { comicDetailRepository.getComicDetail(1) }
        assert(slot.captured is ComicDetailState.EmptyComic)
    }

    @Test
    fun testComicsFailure() = runTest(mainDispatcherRule.getTestDispatcher()) {

        val slot = slot<ComicDetailState>()
        coEvery { comicDetailRepository.getComicDetail(1) } returns flowOf(Result.failure(Throwable()))
        every { _comicDetailState.value = capture(slot) } returns Unit

        comicDetailViewModel.getComicDetail(1)

        coVerify(exactly = 1) { comicDetailRepository.getComicDetail(1) }
        assert(slot.captured is ComicDetailState.Error)
    }

    @After
    fun tearDown(){
        confirmVerified(comicDetailRepository)
    }
}