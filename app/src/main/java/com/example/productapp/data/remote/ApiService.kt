package com.example.productapp.data.remote

import com.example.productapp.data.remote.dto.ProductDto
import com.example.productapp.data.remote.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 100,
        @Query("skip") skip: Int = 0
    ): ProductsResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0
    ): ProductsResponse

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
}