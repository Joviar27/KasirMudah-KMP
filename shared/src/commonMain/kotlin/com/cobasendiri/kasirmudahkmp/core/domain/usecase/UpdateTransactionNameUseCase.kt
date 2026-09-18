package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class UpdateTransactionNameUseCase(
    private val transactionRepository: ITransactionRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("UpdateTransactionNameUseCase")

    suspend fun invoke(transactionId: String, newName: String): Result<Unit>{
        return try {
            val isNameValid = newName.let {
                it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
            }
            if(!isNameValid){
                logWarning("New transaction $transactionId name input is invalid")
                throw KasirMudahException.InvalidInputError
            }

            val result = transactionRepository.updateName(transactionId, newName)
            logInfo("Updated transaction $transactionId name to $newName")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}