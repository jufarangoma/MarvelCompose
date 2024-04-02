package com.jufarangoma.marvelcompose.di

import android.content.Context
import com.jufarangoma.marvelcompose.data.repositories.HeroesLocalRepository
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroeStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.HeroesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Named(LOCAL_HEROE_REPOSITORY)
    @Provides
    @ViewModelScoped
    fun getLocalHeroeRepository(
        @ApplicationContext appContext: Context
    ): HeroesRepository = HeroesLocalRepository(context = appContext)

    @Provides
    fun getHeroesViewModel(
        @Named(LOCAL_HEROE_REPOSITORY) getLocalRepository: HeroesRepository
    ) = HeroesViewModel(
        heroesRepository = getLocalRepository,
        _heroesState = MutableStateFlow(HeroeStates.Loading)
    )
}

private const val LOCAL_HEROE_REPOSITORY = "localHeroeRepository"
