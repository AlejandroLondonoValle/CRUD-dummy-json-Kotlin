package com.sena.dummy_json.domain.model

data class ProductModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String? = null,
    val thumbnail: String = "",
    val images: List<String> = emptyList(),
    val availabilityStatus: String? = null
)