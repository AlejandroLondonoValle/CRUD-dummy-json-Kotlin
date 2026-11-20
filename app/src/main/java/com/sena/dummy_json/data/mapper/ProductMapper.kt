package com.sena.dummy_json.data.mapper

import com.sena.dummy_json.data.remote.dto.product.ProductX
import com.sena.dummy_json.domain.model.ProductModel

fun ProductX.toDomain(): ProductModel = ProductModel(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    thumbnail = thumbnail,
    images = images,
    availabilityStatus = availabilityStatus
)