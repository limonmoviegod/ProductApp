package com.example.productapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.productapp.data.local.ProductEntity
import com.example.productapp.data.remote.ApiService
import com.example.productapp.data.remote.ProductsPagingSource
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val api: ApiService) {

    fun getPagedProducts(query: String = ""): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { ProductsPagingSource(api, query) }
        ).flow
    }
}