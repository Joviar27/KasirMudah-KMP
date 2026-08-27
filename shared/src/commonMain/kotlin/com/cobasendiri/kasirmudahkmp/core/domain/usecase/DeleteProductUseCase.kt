package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository

class DeleteProductUseCase(
    private val productRepository: IProductRepository
) {
    suspend fun invoke(productId: String): Result<Unit> {
        return try {
            val result = productRepository.deleteProduct(productId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}