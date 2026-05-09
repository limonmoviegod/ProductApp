package com.example.productapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.productapp.data.local.ProductEntity
import com.example.productapp.data.remote.dto.toEntity

class ProductsPagingSource(
    private val api: ApiService,
    private val query: String = ""
) : PagingSource<Int, ProductEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val skip = params.key ?: 0
            val response = if (query.isBlank()) {
                api.getProducts(limit = params.loadSize, skip = skip)
            } else {
                api.searchProducts(query = query, limit = params.loadSize, skip = skip)
            }
            val data = response.products.map { it.toEntity() }
            LoadResult.Page(
                data = data,
                prevKey = if (skip == 0) null else skip - params.loadSize,
                nextKey = if (data.isEmpty()) null else skip + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition
    }
}