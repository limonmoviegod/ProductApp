package com.example.productapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.productapp.data.remote.ApiService
import com.example.productapp.data.remote.ProductsPagingSource
import com.example.productapp.data.remote.dto.toDomain
import com.example.productapp.domain.model.Product
import com.example.productapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override fun getPagedProducts(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { ProductsPagingSource(api, query) }
        ).flow
    }

    override suspend fun getProductById(id: Int): Product? {
        return try {
            api.getProducts(limit = 1, skip = id - 1).products.firstOrNull()?.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}