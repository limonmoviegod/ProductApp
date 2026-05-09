package com.example.productapp.data.di

import com.example.productapp.data.remote.ApiService
import com.example.productapp.data.remote.NetworkModule
import com.example.productapp.data.repository.ProductRepositoryImpl
import com.example.productapp.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = NetworkModule.api

    @Provides
    @Singleton
    fun provideProductRepository(api: ApiService): ProductRepository =
        ProductRepositoryImpl(api)
}