package com.jufarangoma.marvelcompose.di

import android.content.Context
import com.jufarangoma.marvelcompose.data.local.LocalProviderHeroes
import com.jufarangoma.marvelcompose.data.repositories.CommonExceptionStrategy
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun getCoroutineDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun getLocalProviderHeroes(
        @ApplicationContext context: Context
    ) = LocalProviderHeroes(context)

    @Named(EXCEPTION_COMMON_STRATEGY)
    @Provides
    @Singleton
    fun getExceptionComicDetailStrategy(): DomainExceptionStrategy = CommonExceptionStrategy()

}

const val EXCEPTION_COMMON_STRATEGY = "exceptionCommonStrategy"
