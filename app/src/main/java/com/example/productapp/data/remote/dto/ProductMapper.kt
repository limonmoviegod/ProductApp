package com.example.productapp.data.remote.dto

import com.example.productapp.data.local.ProductEntity

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    name = title,
    price = price,
    category = "API"
)