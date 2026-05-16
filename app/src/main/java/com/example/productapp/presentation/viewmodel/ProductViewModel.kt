package com.example.productapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.domain.model.Product
import com.example.productapp.domain.usecase.GetProductByIdUseCase
import com.example.productapp.domain.usecase.GetProductsUseCase
import com.example.productapp.domain.usecase.RefreshProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    val products = getProductsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        sync()
    }

    fun sync() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                refreshProductsUseCase()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            _selectedProduct.value = getProductByIdUseCase(id)
        }
    }
}