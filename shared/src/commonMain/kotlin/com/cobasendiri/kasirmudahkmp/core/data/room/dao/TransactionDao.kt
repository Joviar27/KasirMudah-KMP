package com.cobasendiri.kasirmudahkmp.core.data.room.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionBookmarkEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.result.TransactionHistoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTransaction(transaction: TransactionEntity)

    @Query(
        """
        SELECT t.id, t.name, t.total, t.created_at as createdAt, (b.transaction_id IS NOT NULL) as isBookmarked
        FROM transactions as t
        LEFT JOIN transaction_bookmark as b ON t.id = b.transaction_id
        WHERE t.created_at >= :timestampFilter
        ORDER BY t.created_at DESC
    """)
    fun getTransactionHistory(timestampFilter: Long): Flow<List<TransactionHistoryResult>>

    @Query(
        """
        SELECT t.id, t.name, t.total, t.created_at as createdAt, 1 as isBookmarked
        FROM transactions as t
        INNER JOIN transaction_bookmark as b ON t.id = b.transaction_id
        ORDER BY b.bookmarked_at DESC
    """)
    fun getBookmarkedTransaction(): Flow<List<TransactionHistoryResult>>

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransaction(transactionId: String)

    @Query("SELECT *FROM transactions as t WHERE t.id = :transactionId")
    suspend fun getTransaction(transactionId: String): TransactionEntity

    @Query("SELECT EXISTS(SELECT 1 FROM transaction_bookmark WHERE transaction_id = :transactionId)")
    fun isBookmarked(transactionId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(bookmark: TransactionBookmarkEntity)

    @Query("DELETE FROM transaction_bookmark WHERE transaction_id = :transactionId")
    suspend fun removeBookmark(transactionId: String)

    @Transaction
    suspend fun updateBookmark(transactionId: String, timestamp: Long): Boolean{
        return if(isBookmarked(transactionId).firstOrNull() == true){
            removeBookmark(transactionId)
            false
        }else{
            addBookmark(TransactionBookmarkEntity(transactionId, timestamp))
            true
        }
    }

    @Query("UPDATE transactions SET name = :newName WHERE id = :transactionId")
    suspend fun updateName(transactionId: String, newName: String)
}