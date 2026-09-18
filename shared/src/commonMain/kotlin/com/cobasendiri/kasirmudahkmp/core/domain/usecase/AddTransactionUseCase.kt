package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.firstOrNull

class AddTransactionUseCase(
    private val transactionRepository: ITransactionRepository,
    private val cartRepository: ICartRepository,
    private val profileRepository: IProfileRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("AddTransactionUseCase")

    suspend fun invoke(): Result<Unit>{
        return try {
            val transactionItems = cartRepository.getProductsTotal()
            if(transactionItems.isEmpty()){
                logWarning("Transaction items is empty")
                throw KasirMudahException.TransactionAmountInvalidError
            }

            val transactionTotal = cartRepository.getTotalCartAmount().firstOrNull()
                ?: transactionItems.sumOf { it.itemTotal }

            if(transactionTotal == 0L){
                logWarning("Transaction total amount is 0")
                throw KasirMudahException.TransactionAmountInvalidError
            }

            val shopName = profileRepository.getShopProfile().firstOrNull()?.shopName ?: ""

            val result = transactionRepository.insertNewTransaction(
                transactionItems,
                transactionTotal,
                shopName
            )
            logInfo("Added new transaction. " +
                    "Shop Name: $shopName " +
                    "Total: $transactionTotal " +
                    "Items: $transactionItems")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}