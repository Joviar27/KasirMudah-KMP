package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetIsTransactionBookmarkedUseCase(
    private val transactionRepository: ITransactionRepository
) {
    fun invoke(transactionId: String): Flow<Result<Boolean>>{
        return transactionRepository.getIsBookmarked(transactionId)
            .map {
                Result.Success(it)
            }.catch {
                Result.Error(it)
            }
    }
}