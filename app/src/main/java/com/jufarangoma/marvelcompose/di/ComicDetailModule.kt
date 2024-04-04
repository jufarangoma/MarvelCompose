package com.jufarangoma.marvelcompose.di

import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.data.repositories.ComicDetailRepositoryImpl
import com.jufarangoma.marvelcompose.domain.repositories.ComicDetailRepository
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicDetailState
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object ComicDetailModule {

    @Provides
    @ViewModelScoped
    fun getComicDetailRepository(
        marvelApi: MarvelApi,
        @Named(EXCEPTION_COMMON_STRATEGY) exceptionComicDetail: DomainExceptionStrategy
    ): ComicDetailRepository = ComicDetailRepositoryImpl(marvelApi, exceptionComicDetail)

    @Provides
    fun getComicDetailViewModel(
        comicsRepository: ComicDetailRepository,
        coroutineDispatcher: CoroutineDispatcher
    ) = ComicDetailViewModel(
        comicsRepository,
        MutableStateFlow(ComicDetailState.Loading),
        coroutineDispatcher
    )
}