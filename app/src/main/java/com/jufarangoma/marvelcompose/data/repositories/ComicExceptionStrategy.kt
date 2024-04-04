package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.domain.entities.exceptions.DomainException
import com.jufarangoma.marvelcompose.domain.entities.exceptions.HttpCatchException
import com.jufarangoma.marvelcompose.domain.entities.exceptions.UnathorizedException
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import retrofit2.HttpException
import java.net.HttpURLConnection

class ComicExceptionStrategy : DomainExceptionStrategy {
    override fun manageError(error: Throwable): DomainException {
        return if (error is HttpException) {
            when (error.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> UnathorizedException
                else -> HttpCatchException(error.code())
            }
        } else {
            DomainException(error.message)
        }
    }
}