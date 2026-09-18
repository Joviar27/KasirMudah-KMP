package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class UpdateProductUseCase(
    private val productRepository: IProductRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("UpdateProductUseCase")

    suspend fun invoke(productDraft: ProductDraft): Result<Unit> {
        return try {
            val isNameValid = productDraft.let {
                it.name.isNotBlank() && it.name.firstOrNull()?.isWhitespace() == false
            }
            val isPriceValid = productDraft.let {
                it.price.isNotBlank() && !it.price.contains(" ")
                        && !it.price.all { it == '0' }
            }

            if(!isNameValid || !isPriceValid){
                logWarning("Product ${productDraft.id} name or price input is not valid")
                throw KasirMudahException.InvalidInputError
            }

            val result = productRepository.updateProduct(productDraft)
            logInfo("Product updated: $productDraft")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}