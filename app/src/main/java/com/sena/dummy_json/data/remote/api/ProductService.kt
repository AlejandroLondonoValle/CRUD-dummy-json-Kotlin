package com.sena.dummy_json.data.remote.api

import com.sena.dummy_json.data.remote.dto.product.PatchProduct
import com.sena.dummy_json.data.remote.dto.product.ProductListResponse
import retrofit2.http.GET
import com.sena.dummy_json.data.remote.dto.product.Product
import com.sena.dummy_json.data.remote.dto.product.ProductX
import com.sena.dummy_json.data.remote.dto.product.UpdateProductRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface ProductService {

    @GET("products")
    suspend fun getProducts() : Product

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id : Int
    ) : ProductX

    @DELETE("products/{id}")
    suspend fun deleteProductById(
        @Path("id") id : Int
    ) : ProductX

    @PATCH("products/{id}")
    suspend fun patchProductById(
        @Path("id") id: Int,
        @Body product: PatchProduct
    ) : ProductX

    @POST("products/add")
    suspend fun addProduct(
        @Body product : UpdateProductRequest
    ) : ProductX

    @PUT("products/{id}")
    suspend fun putProduct(
        @Path("id") id: Int,
        @Body product: UpdateProductRequest
    ) : ProductX
}
