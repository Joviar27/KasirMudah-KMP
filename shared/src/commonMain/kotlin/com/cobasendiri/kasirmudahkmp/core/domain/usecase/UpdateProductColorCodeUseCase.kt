package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository

class UpdateProductColorCodeUseCase(
    private val productRepository: IProductRepository
) {
    suspend fun invoke(productId: String, newColor: Long): Result<Unit> {
        return try {
            val result = productRepository.updateProductColorCode(productId, newColor)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}