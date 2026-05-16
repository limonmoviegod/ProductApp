package com.example.productapp.data.remote.dto

import com.example.productapp.data.local.ProductEntity
import com.example.productapp.domain.model.Product
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val category: String = ""
)

@JsonClass(generateAdapter = true)
data class ProductsResponse(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

fun ProductDto.toDomain() = Product(
    id = id,
    name = title,
    price = price,
    category = category
)

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    name = title,
    price = price,
    category = category
)

fun ProductEntity.toDomain() = Product(
    id = id,
    name = name,
    price = price,
    category = category
)