package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetBookmarkedTransactionUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetBookmarkedTransactionUseCase")

    fun invoke(): Flow<Result<List<TransactionHistory>>>{
        return transactionRepository.getBookmarkedTransaction().map {
            if(it==null){
                logWarning("Bookmarked transaction is null")
                Result.Error(KasirMudahException.UnknownError(null))
            }else{
                logInfo("Bookmarked Transaction: $it")
                Result.Success(it)
            }
        }.catch {
            Result.Error(it)
        }
    }
}