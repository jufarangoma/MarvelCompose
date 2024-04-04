package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.exceptions.DomainException

interface DomainExceptionStrategy {
    fun manageError(error: Throwable): DomainException
}