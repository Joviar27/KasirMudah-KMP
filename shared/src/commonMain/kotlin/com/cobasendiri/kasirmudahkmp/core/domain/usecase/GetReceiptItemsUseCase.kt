package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository

class GetReceiptItemsUseCase(
    private val cartRepository: ICartRepository
) {
    suspend fun invoke(): Result<List<TransactionItemInfo>>{
        return try {
            val result = cartRepository.getProductsTotal()
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}