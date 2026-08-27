package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository

class IncrementProductUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(productId: String): Result<Unit>{
        return try {
            val result = cartRepository.addOrIncrementProduct(productId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}