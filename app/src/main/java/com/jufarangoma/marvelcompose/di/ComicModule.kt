package com.jufarangoma.marvelcompose.di

import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.data.repositories.ComicExceptionStrategy
import com.jufarangoma.marvelcompose.data.repositories.ComicsRepositoryImpl
import com.jufarangoma.marvelcompose.domain.repositories.ComicsRepository
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.ComicsViewModel
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
object ComicModule {

    @Named(EXCEPTION_COMIC_STRATEGY)
    @Provides
    @ViewModelScoped
    fun getExceptionComicStrategy(): DomainExceptionStrategy = ComicExceptionStrategy()

    @Provides
    @ViewModelScoped
    fun getComicsRepository(
        marvelApi: MarvelApi,
        @Named(EXCEPTION_COMIC_STRATEGY) exceptionComics: DomainExceptionStrategy
    ): ComicsRepository = ComicsRepositoryImpl(marvelApi, exceptionComics)

    @Provides
    fun getComicsViewModel(
        comicsRepository: ComicsRepository,
        coroutineDispatcher: CoroutineDispatcher
    ) = ComicsViewModel(
        comicsRepository,
        MutableStateFlow(ComicStates.Loading),
        coroutineDispatcher
    )
}

private const val EXCEPTION_COMIC_STRATEGY = "exceptionComicsStrategy"
