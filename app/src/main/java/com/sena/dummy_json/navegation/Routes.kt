package com.sena.dummy_json.navegation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object ProductList : Routes

    @Serializable
    data class ProductDetail(val productId: Int) : Routes

    @Serializable
    data class ProductForm(val productId: Int? = null) : Routes
}
