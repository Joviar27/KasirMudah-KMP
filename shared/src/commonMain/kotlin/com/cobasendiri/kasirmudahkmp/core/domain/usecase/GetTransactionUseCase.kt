package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class GetTransactionUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetTransactionUseCase")

    suspend fun invoke(transactionId: String): Result<TransactionReceipt>{
        return try {
            val result = transactionRepository.getTransaction(transactionId)
            logInfo("Transaction $transactionId: $result")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}