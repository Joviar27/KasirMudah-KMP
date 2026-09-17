package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class AddProductUseCase(
    private val productRepository: IProductRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("AddProductUseCase")

    suspend fun invoke(newProduct: ProductDraft): Result<Unit>{
        return try {
            val result = productRepository.addNewProduct(newProduct)
            logInfo("Added new product: $newProduct")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
   }
}