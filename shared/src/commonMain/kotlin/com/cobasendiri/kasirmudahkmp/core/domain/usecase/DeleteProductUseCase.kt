package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class DeleteProductUseCase(
    private val productRepository: IProductRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("DeleteProductUseCase")

    suspend fun invoke(productId: String): Result<Unit> {
        return try {
            val result = productRepository.deleteProduct(productId)
            logInfo("Deleted product: $productId")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}