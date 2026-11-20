package com.sena.dummy_json.ui.state

import com.sena.dummy_json.domain.model.ProductModel

data class ProductUIState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val selectedProduct: ProductModel? = null,
    val isFormOpen: Boolean = false,
    val productToEdit: ProductModel? = null,
    val productToDelete: ProductModel? = null,
    val searchQuery: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)
