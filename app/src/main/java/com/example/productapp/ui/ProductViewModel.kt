package com.example.productapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.productapp.data.local.ProductEntity
import com.example.productapp.data.remote.NetworkModule
import com.example.productapp.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProductViewModel : ViewModel() {

    private val repository = ProductRepository(NetworkModule.api)

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val pagedProducts: Flow<PagingData<ProductEntity>> = _query
        .debounce(300)
        .flatMapLatest { query ->
            repository.getPagedProducts(query)
        }
        .cachedIn(viewModelScope)

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}