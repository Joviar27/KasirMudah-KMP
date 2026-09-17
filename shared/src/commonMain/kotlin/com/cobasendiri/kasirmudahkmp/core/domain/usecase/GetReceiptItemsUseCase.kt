package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class GetReceiptItemsUseCase(
    private val cartRepository: ICartRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetReceiptItemsUseCase")

    suspend fun invoke(): Result<List<TransactionItemInfo>>{
        return try {
            val result = cartRepository.getProductsTotal()
            logInfo("List of item in receipt: $result")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}