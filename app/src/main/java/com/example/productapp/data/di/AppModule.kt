package com.example.productapp.data.di

import android.content.Context
import com.example.productapp.data.local.AppDatabase
import com.example.productapp.data.local.ProductDao
import com.example.productapp.data.remote.ApiService
import com.example.productapp.data.remote.NetworkModule
import com.example.productapp.data.repository.ProductRepositoryImpl
import com.example.productapp.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideProductRepository(
        api: ApiService,
        dao: ProductDao
    ): ProductRepository = ProductRepositoryImpl(api, dao)
}