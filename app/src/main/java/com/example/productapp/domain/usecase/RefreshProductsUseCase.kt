package com.example.productapp.domain.usecase

import com.example.productapp.domain.repository.ProductRepository
import javax.inject.Inject

class RefreshProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.refreshProducts()
}