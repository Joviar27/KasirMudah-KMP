package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetIsTransactionBookmarkedUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetIsTransactionBookmarkedUseCase")

    fun invoke(transactionId: String): Flow<Result<Boolean>>{
        return transactionRepository.getIsBookmarked(transactionId)
            .map {
                logInfo("Current transaction $transactionId isBookmarked: $it")
                Result.Success(it)
            }.catch {
                Result.Error(it)
            }
    }
}