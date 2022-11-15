package com.katiegarcia.flickrdemo.activity.module

import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
Application Module class for base implementation of reusable instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object TestApplicationModule {

    @Provides
    @Singleton
    fun getFlickrAPI(retrofit: Retrofit): FlickrAPI = retrofit.create(FlickrAPI::class.java)

    @Provides
    @Singleton
    fun getRetroFit(): Retrofit = Retrofit.Builder()
        .baseUrl(FlickrAPI.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}