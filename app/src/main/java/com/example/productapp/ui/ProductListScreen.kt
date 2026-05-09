package com.example.productapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.productapp.ui.components.ProductItem
import com.example.productapp.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductViewModel = viewModel()) {
    val query by viewModel.query.collectAsState()
    val products = viewModel.pagedProducts.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Product List") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchBar(query, viewModel::onQueryChange)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products.itemCount) { index ->
                    val item = products[index]
                    if (item != null) ProductItem(item)
                }
                products.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(Modifier.padding(16.dp))
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(Modifier.padding(16.dp))
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            item {
                                Text("Помилка завантаження", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}