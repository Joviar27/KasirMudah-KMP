package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository

class GetTransactionUseCase(
    private val transactionRepository: ITransactionRepository
) {
    suspend fun invoke(transactionId: String): Result<TransactionReceipt>{
        return try {
            val result = transactionRepository.getTransaction(transactionId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}