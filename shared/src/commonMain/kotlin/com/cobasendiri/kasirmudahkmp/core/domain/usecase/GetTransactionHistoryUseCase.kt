package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.filter.DateFilter
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlin.time.Clock

class GetTransactionHistoryUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetTransactionHistoryUseCase")

    fun invoke(timestampFilter: DateFilter): Flow<Result<List<TransactionHistory>>>{
        val timestamp = calculateTimestamp(timestampFilter)
        return transactionRepository.getTransactionHistory(timestamp).map {
            if(it==null){
                logWarning("Transaction history is null")
                Result.Error(KasirMudahException.UnknownError(null))
            }else{
                logInfo("Transaction history list: $it")
                Result.Success(it)
            }
        }.catch {
            Result.Error(it)
        }
    }

    private fun calculateTimestamp(filter: DateFilter): Long = when (filter) {
        DateFilter.ALL_TIME -> 0L
        DateFilter.TODAY -> Clock.System.now().toEpochMilliseconds()/1000 - 86400L
        DateFilter.LAST_WEEK -> Clock.System.now().toEpochMilliseconds()/1000 - (86400L * 7)
        DateFilter.LAST_MONTH -> Clock.System.now().toEpochMilliseconds()/1000 - (86400L * 30)
    }
}