package com.adrianbucayan.myrecipeapp.di

import android.content.Context
import com.adrianbucayan.myrecipeapp.common.Constants
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.adrianbucayan.myrecipeapp.data.remote.HeaderInterceptor
import com.adrianbucayan.myrecipeapp.data.remote.LoggingInterceptor
import com.adrianbucayan.myrecipeapp.data.remote.MyRecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesAuthorizationInterceptor(@ApplicationContext context: Context): HeaderInterceptor {

        return HeaderInterceptor(context)
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = object: LoggingInterceptor() {}
        val loggingInterceptor = HttpLoggingInterceptor(logger)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context,
                             loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val appHeaderInterceptor = HeaderInterceptor(context)
        val cacheSize = 10 * 1024 * 1024
        val cache = okhttp3.Cache(context.cacheDir, cacheSize.toLong())
       var retry: Boolean = true

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(appHeaderInterceptor)
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(retry)
            .build()

        return okHttpClient
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): MyRecipeApi {
        return retrofit
            .build()
            .create(MyRecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun getObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(JodaModule())

        return objectMapper
    }

}
