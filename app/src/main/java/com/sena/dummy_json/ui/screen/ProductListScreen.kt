package com.sena.dummy_json.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.dummy_json.ui.composable.ConfirmDeleteDialog
import com.sena.dummy_json.ui.section.*
import com.sena.dummy_json.ui.viewModel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    onAddProductClick: () -> Unit,
    onEditProductClick: (Int) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.successMessage, state.errorMessage) {
        state.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marketplace") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { viewModel.loadProducts() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddProductClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo Producto") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        val modifier = Modifier.padding(padding)

        when {
            state.isLoading && state.products.isEmpty() -> LoadingSection(modifier)

            state.errorMessage != null && state.products.isEmpty() -> ErrorSection(
                message = state.errorMessage!!,
                onRetry = { viewModel.loadProducts() },
                modifier = modifier
            )

            state.products.isEmpty() -> EmptySection(modifier)

            else -> ProductListSection(
                products = state.products,
                searchQuery = state.searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onProductClick = { product -> onProductClick(product.id) },
                onEditClick = { product -> onEditProductClick(product.id) },
                onDeleteClick = { product -> viewModel.requestDeleteProduct(product) },
                modifier = modifier
            )
        }
    }

    // Confirm Delete Dialog
    state.productToDelete?.let { product ->
        ConfirmDeleteDialog(
            product = product,
            onConfirm = { viewModel.deleteProduct(it) },
            onDismiss = { viewModel.cancelDelete() }
        )
    }
}
