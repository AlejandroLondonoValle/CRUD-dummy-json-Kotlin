package com.sena.dummy_json.data.remote.dto.product

import com.squareup.moshi.Json

data class PatchProduct(
    @param:Json(name = "category")
    val category: String
)
