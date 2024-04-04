package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.domain.entities.exceptions.DomainException
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy

class CommonExceptionStrategy : DomainExceptionStrategy {

    override fun manageError(error: Throwable): DomainException {
        return DomainException("Implement other strategy to this endpoint")
    }

}
