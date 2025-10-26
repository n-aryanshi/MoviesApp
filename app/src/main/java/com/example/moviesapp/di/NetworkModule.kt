package com.example.moviesapp.di

import com.example.moviesapp.view.Shows
import com.example.moviesapp.model.remote.Title
import com.example.moviesapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

///https://api.watchmode.com/v1/list-titles/?apiKey=N9r1loJ0bj88WwwLEGUS1tcRs9Wh9fMdPeCeotwi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.watchmode.com/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
    //retrofit need client

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(
        retrofit: Retrofit
    ): ApiService =  retrofit.create(ApiService::class.java)



}