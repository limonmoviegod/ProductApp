package com.example.productapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.local.AppDatabase
import com.example.productapp.data.local.ProductEntity
import com.example.productapp.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).productDao()
    private val repository = ProductRepository(dao)

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val products = _query
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) repository.getProducts()
            else repository.searchProducts(query)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList<ProductEntity>())

    init {
        viewModelScope.launch { repository.refreshCache() }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}