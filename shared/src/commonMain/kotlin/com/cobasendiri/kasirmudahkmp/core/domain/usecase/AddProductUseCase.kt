package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository

class AddProductUseCase(
    private val productRepository: IProductRepository
) {
   suspend fun invoke(newProduct: ProductDraft): Result<Unit>{
        return try {
            val result = productRepository.addNewProduct(newProduct)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
   }
}