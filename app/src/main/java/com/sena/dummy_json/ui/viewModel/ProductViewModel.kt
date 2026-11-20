package com.sena.dummy_json.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.dummy_json.domain.model.ProductModel
import com.sena.dummy_json.domain.repository.ProductRepository
import com.sena.dummy_json.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.getProducts() }
                .onSuccess { list ->
                    _uiState.update { it.copy(isLoading = false, products = list) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "No se pudieron cargar los productos"
                        )
                    }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectProduct(product: ProductModel?) {
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun openCreateForm() {
        _uiState.update { it.copy(isFormOpen = true, productToEdit = null) }
    }

    fun openEditForm(product: ProductModel) {
        _uiState.update { it.copy(isFormOpen = true, productToEdit = product, selectedProduct = null) }
    }

    fun closeForm() {
        _uiState.update { it.copy(isFormOpen = false, productToEdit = null) }
    }

    fun saveProduct(product: ProductModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            val isEditing = product.id != 0

            runCatching {
                if (isEditing) {
                    repository.updateProduct(product)
                } else {
                    repository.createProduct(product)
                }
            }.onSuccess { savedProduct ->
                _uiState.update { state ->
                    val updatedList = if (isEditing) {
                        state.products.map { if (it.id == savedProduct.id) savedProduct else it }
                    } else {
                        // DummyJSON assigns an ID or we prepend it to local list
                        val newItem = if (savedProduct.id == 0) {
                            savedProduct.copy(id = (state.products.maxOfOrNull { it.id } ?: 0) + 1)
                        } else savedProduct
                        listOf(newItem) + state.products
                    }
                    state.copy(
                        isSaving = false,
                        isFormOpen = false,
                        productToEdit = null,
                        products = updatedList,
                        successMessage = if (isEditing) "Producto actualizado con éxito" else "Producto creado con éxito"
                    )
                }
            }.onFailure { e ->
                // Fallback for local update if API returns error (since API might simulate errors for new IDs)
                _uiState.update { state ->
                    val updatedList = if (isEditing) {
                        state.products.map { if (it.id == product.id) product else it }
                    } else {
                        val fakeId = (state.products.maxOfOrNull { it.id } ?: 0) + 1
                        listOf(product.copy(id = fakeId)) + state.products
                    }
                    state.copy(
                        isSaving = false,
                        isFormOpen = false,
                        productToEdit = null,
                        products = updatedList,
                        successMessage = if (isEditing) "Producto actualizado (modo local)" else "Producto creado (modo local)"
                    )
                }
            }
        }
    }

    fun requestDeleteProduct(product: ProductModel) {
        _uiState.update { it.copy(productToDelete = product, selectedProduct = null) }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(productToDelete = null) }
    }

    fun deleteProduct(product: ProductModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.deleteProduct(product.id) }
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            productToDelete = null,
                            products = state.products.filterNot { it.id == product.id },
                            successMessage = "Producto eliminado con éxito"
                        )
                    }
                }
                .onFailure {
                    // Local removal fallback
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            productToDelete = null,
                            products = state.products.filterNot { it.id == product.id },
                            successMessage = "Producto eliminado (modo local)"
                        )
                    }
                }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
