package com.jufarangoma.marvelcompose.domain.entities.exceptions

open class DomainException(override val message: String? = "") : Throwable(message)
object UnathorizedException : DomainException()
class HttpCatchException(code: Int) : DomainException()