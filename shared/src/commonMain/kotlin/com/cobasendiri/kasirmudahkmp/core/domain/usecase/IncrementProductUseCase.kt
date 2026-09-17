package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class IncrementProductUseCase(
    private val cartRepository: ICartRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("IncrementProductUseCase")

    suspend fun invoke(productId: String): Result<Unit>{
        return try {
            val result = cartRepository.addOrIncrementProduct(productId)
            logInfo("Increment product: $productId")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}