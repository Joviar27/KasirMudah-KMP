package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository

class UpdateTransactionBookmarkUseCase(
    private val transactionRepository: ITransactionRepository
) {
    suspend fun invoke(transactionId: String): Result<Boolean>{
        return try {
            val result = transactionRepository.updateBookmark(transactionId)
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}