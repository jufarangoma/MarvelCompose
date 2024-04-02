package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.domain.entities.exceptions.DomainException
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy

class ComicExceptionStrategy : DomainExceptionStrategy {
    override fun manageError(error: Throwable): DomainException {
        return DomainException(error.message)
    }
}