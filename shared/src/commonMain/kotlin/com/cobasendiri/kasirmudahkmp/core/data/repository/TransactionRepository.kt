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

    companion object{
        private const val LOGGER_TAG = "TransactionRepository"
    }

    override suspend fun insertNewTransaction(
        draftItems: List<TransactionItemInfo>,
        draftTotal: Long,
        shopName: String
    ) = withContext(ioDispatcher){
        val log = Pair(LOGGER_TAG, "insertNewTransaction")
        runMapExceptionSuspending(log) {
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
        val log = Pair(LOGGER_TAG, "deleteTransaction")
        runMapExceptionSuspending(log) {
            transactionDao.deleteTransaction(transactionId)
        }
    }

    override fun getTransactionHistory(timestampFilter: Long): Flow<List<TransactionHistory>?> {
        val log = Pair(LOGGER_TAG, "getTransactionHistory")
        return transactionDao.getTransactionHistory(timestampFilter).mapExceptionFlow(log) {
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override suspend fun getTransaction(transactionId: String): TransactionReceipt {
        val log = Pair(LOGGER_TAG, "getTransaction")
        return withContext(ioDispatcher) {
            runMapExceptionSuspending(log) {
                transactionDao.getTransaction(transactionId).mapToTransactionReceipt()
            }
        }
    }

    override fun getBookmarkedTransaction(): Flow<List<TransactionHistory>?> {
        val log = Pair(LOGGER_TAG, "getBookmarkedTransaction")
        return transactionDao.getBookmarkedTransaction().mapExceptionFlow(log){
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override fun getIsBookmarked(transactionId: String): Flow<Boolean> {
        val log = Pair(LOGGER_TAG, "getIsBookmarked")
        return transactionDao.isBookmarked(transactionId)
            .mapExceptionFlow(log).flowOn(ioDispatcher)
    }

    override suspend fun updateBookmark(transactionId: String): Boolean {
        val log = Pair(LOGGER_TAG, "updateBookmark")
        return withContext(ioDispatcher){
            runMapExceptionSuspending(log) {
                transactionDao.updateBookmark(
                    transactionId,
                    Clock.System.now().toEpochMilliseconds()/1000
                )
            }
        }
    }

    override suspend fun updateName(transactionId: String, newName: String) {
        val log = Pair(LOGGER_TAG, "updateName")
        return withContext(ioDispatcher) {
            runMapExceptionSuspending(log) {
                transactionDao.updateName(transactionId, newName)
            }
        }
    }
}