package com.cobasendiri.kasirmudahkmp.core.domain.repository

import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllProducts(searchQuery: String): Flow<List<ProductInfo>?>

    suspend fun addNewProduct(newProduct: ProductDraft)

    suspend fun updateProduct(productDraft: ProductDraft)

    suspend fun updateProductColorCode(productId: String, newColor: Long)

    suspend fun deleteProduct(productId: String)

}