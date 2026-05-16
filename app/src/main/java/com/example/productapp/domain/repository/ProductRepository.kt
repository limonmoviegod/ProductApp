package com.example.productapp.domain.repository

import com.example.productapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun refreshProducts()
    suspend fun getProductById(id: Int): Product?
}