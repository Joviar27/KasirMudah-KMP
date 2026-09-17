package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class DeleteTransactionHistoryUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("DeleteTransactionHistoryUseCase")

    suspend fun invoke(transactionId: String): Result<Unit>{
        return try {
            val result = transactionRepository.deleteTransaction(transactionId)
            logInfo("Deleted transaction: $transactionId")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}