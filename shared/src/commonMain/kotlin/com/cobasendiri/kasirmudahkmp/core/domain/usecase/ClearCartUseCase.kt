package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.Result

class ClearCartUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(): Result<Unit> {
        return try {
            val result = cartRepository.clearCart()
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}