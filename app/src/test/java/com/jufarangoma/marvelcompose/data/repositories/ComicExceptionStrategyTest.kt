package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.domain.entities.exceptions.HttpCatchException
import com.jufarangoma.marvelcompose.domain.entities.exceptions.UnathorizedException
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ComicExceptionStrategyTest {

    private lateinit var domainExceptionStrategy: DomainExceptionStrategy

    @Before
    fun setUp() {
        domainExceptionStrategy = ComicExceptionStrategy()
    }

    @Test
    fun testUnauthorizedException() {
        val exception = mockk<HttpException>()
        every { exception.code() } answers { 401 }
        val result = domainExceptionStrategy.manageError(exception)

        assert(result is UnathorizedException)

        verify { exception.code() }
        confirmVerified(exception)
    }

    @Test
    fun testOtherHttpException() {
        val exception = mockk<HttpException>()
        every { exception.code() } answers { 404 }
        val result = domainExceptionStrategy.manageError(exception)

        assert(result is HttpCatchException)

        verify { exception.code() }
        confirmVerified(exception)
    }
    @Test
    fun testOtherException() {
        val exception = Throwable("Fallo")

        val result = domainExceptionStrategy.manageError(exception)

        assert(result.message == "Fallo")
    }
}