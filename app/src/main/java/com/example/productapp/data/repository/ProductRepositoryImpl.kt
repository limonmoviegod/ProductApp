package com.example.productapp.data.repository

import com.example.productapp.data.local.ProductDao
import com.example.productapp.data.local.ProductEntity
import com.example.productapp.data.remote.ApiService
import com.example.productapp.data.remote.dto.toDomain
import com.example.productapp.data.remote.dto.toEntity
import com.example.productapp.domain.model.Product
import com.example.productapp.domain.repository.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshProducts() {
        coroutineScope {
            val productsDeferred = async { api.getProducts(limit = 100).products }
            val categoriesDeferred = async { api.getCategories() }

            val products = productsDeferred.await()
            categoriesDeferred.await()

            val entities: List<ProductEntity> = products.map { it.toEntity() }
            dao.clearAll()
            dao.insertAll(entities)
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        return try {
            dao.getById(id)?.toDomain() ?: api.getProductById(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }
}