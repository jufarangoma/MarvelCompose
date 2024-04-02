package com.jufarangoma.marvelcompose.di

import android.content.Context
import com.jufarangoma.marvelcompose.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getPrivateKey(
        context: Context
    ) = context.getString(R.string.MARVEL_PRIVATE_API_KEY)

    @Provides
    @Singleton
    fun getPublicKey(
        context: Context
    ) = context.getString(R.string.MARVEL_PUBLIC_API_KEY)

    @Provides
    @Singleton
    fun authInterceptor(
        context: Context
    ) = { chain: Interceptor.Chain ->
        val ts = System.currentTimeMillis()

        val hash =
            "$ts${getPrivateKey(context)}${getPublicKey(context)}".md5()
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("ts", ts.toString())
            .addQueryParameter("apikey", getPublicKey(context))
            .addQueryParameter("hash", hash)
            .build()
        val updated = request.newBuilder()
            .url(url)
            .build()

        chain.proceed(updated)
    }

    @Provides
    @Singleton
    fun okHttpProvider(
        @ApplicationContext context: Context
    ): OkHttpClient = OkHttpClient.Builder()
        .dispatcher(Dispatcher().apply { maxRequests = 1 })
        .addInterceptor(authInterceptor(context))
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .apply {
            addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        }
        .build()

    @Provides
    @Singleton
    fun retrofitProvider(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(MARVEL_BASE_URL)
            .build()
    }

}

private const val TIMEOUT = 50L
private const val MARVEL_BASE_URL = "https://gateway.marvel.com/v1/public/"

private fun String.md5(): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16).padStart(32, '0')
