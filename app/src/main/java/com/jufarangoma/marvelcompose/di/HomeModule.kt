package com.jufarangoma.marvelcompose.di

import com.jufarangoma.marvelcompose.data.local.LocalProviderHeroes
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.data.repositories.HeroesRepositoryImpl
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.HeroesViewModel
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
object HomeModule {

    @Provides
    @ViewModelScoped
    fun getHeroesRepository(
        marvelApi: MarvelApi,
        localProviderHeroes: LocalProviderHeroes,
        @Named(EXCEPTION_COMMON_STRATEGY) domainExceptionStrategy: DomainExceptionStrategy
    ): HeroesRepository = HeroesRepositoryImpl(
        marvelApi,
        localProviderHeroes,
        domainExceptionStrategy
    )

    @Provides
    fun getHeroesViewModel(
        getHeroesRepository: HeroesRepository,
        coroutineDispatcher: CoroutineDispatcher
    ) = HeroesViewModel(
        heroesRepository = getHeroesRepository,
        _heroesState = MutableStateFlow(HeroStates.Loading),
        coroutineDispatcher
    )
}

