package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class UpdateProductColorCodeUseCase(
    private val productRepository: IProductRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("UpdateProductColorCodeUseCase")

    suspend fun invoke(productId: String, newColor: Long): Result<Unit> {
        return try {
            val result = productRepository.updateProductColorCode(productId, newColor)
            logInfo("Updated product $productId color to $newColor")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}