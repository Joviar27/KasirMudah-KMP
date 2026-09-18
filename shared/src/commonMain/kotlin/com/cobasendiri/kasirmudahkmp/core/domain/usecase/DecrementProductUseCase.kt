package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class DecrementProductUseCase(
    private val cartRepository: ICartRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("DecrementProductUseCase")

    suspend fun invoke(productId: String): Result<Unit> {
        return try {
            val result = cartRepository.decrementProduct(productId)
            logInfo("Decrement product: $productId")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}