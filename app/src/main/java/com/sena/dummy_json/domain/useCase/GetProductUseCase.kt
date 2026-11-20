package com.sena.dummy_json.domain.useCase

import com.sena.dummy_json.domain.model.ProductModel
import com.sena.dummy_json.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductModel> = repository.getProducts()

    suspend fun byId(id: Int): ProductModel = repository.getProductById(id)
}