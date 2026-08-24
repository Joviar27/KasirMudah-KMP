package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.filter.DateFilter
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import kotlin.time.Clock

class GetTransactionHistoryUseCase(
    private val transactionRepository: ITransactionRepository
) {

    fun invoke(timestampFilter: DateFilter): Flow<Result<List<TransactionHistory>>>{
        val timestamp = calculateTimestamp(timestampFilter)
        return transactionRepository.getTransactionHistory(timestamp).map {
            if(it==null){
                Result.Error(KasirMudahException.UnknownError(null))
            }else{
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