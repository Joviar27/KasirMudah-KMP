package com.cobasendiri.kasirmudahkmp.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.TransactionDao
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntityItem
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapToTransactionReceipt
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapTransactionHistoryToDomain
import com.cobasendiri.kasirmudahkmp.core.data.util.IdGenerator
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Clock

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ITransactionRepository {

    override suspend fun insertNewTransaction(
        draftItems: List<TransactionItemInfo>,
        draftTotal: Long,
        shopName: String
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            val id = IdGenerator.generateTransactionId()
            val name = "Transaksi ${id.takeLast(10)}${id.take(10)}"
            val items = draftItems.map {
                TransactionEntityItem(
                    name = it.name,
                    count = it.count,
                    total = it.itemTotal
                )
            }

            val transaction = TransactionEntity(
                id = id,
                name = name,
                shopName = shopName,
                createdAt = Clock.System.now().toEpochMilliseconds()/1000,
                items = items,
                total = draftTotal
            )

            transactionDao.insertNewTransaction(transaction)
        }
    }

    override suspend fun deleteTransaction(
        transactionId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            transactionDao.deleteTransaction(transactionId)
        }
    }

    override fun getTransactionHistory(timestampFilter: Long): Flow<List<TransactionHistory>?> {
        return transactionDao.getTransactionHistory(timestampFilter).mapExceptionFlow {
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override suspend fun getTransaction(transactionId: String): TransactionReceipt {
        return withContext(ioDispatcher) {
            runMapExceptionSuspending {
                transactionDao.getTransaction(transactionId).mapToTransactionReceipt()
            }
        }
    }

    override fun getBookmarkedTransaction(): Flow<List<TransactionHistory>?> {
        return transactionDao.getBookmarkedTransaction().mapExceptionFlow{
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override fun getIsBookmarked(transactionId: String): Flow<Boolean> {
        return transactionDao.isBookmarked(transactionId)
            .mapExceptionFlow().flowOn(ioDispatcher)
    }

    override suspend fun updateBookmark(transactionId: String): Boolean {
        return withContext(ioDispatcher){
            runMapExceptionSuspending {
                transactionDao.updateBookmark(
                    transactionId,
                    Clock.System.now().toEpochMilliseconds()/1000
                )
            }
        }
    }

    override suspend fun updateName(transactionId: String, newName: String) {
        return withContext(ioDispatcher) {
            runMapExceptionSuspending {
                transactionDao.updateName(transactionId, newName)
            }
        }
    }
}