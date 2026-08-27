package com.cobasendiri.kasirmudahkmp.core.data.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(
    tableName = "transaction_bookmark",
    foreignKeys = [
        ForeignKey(
            entity = TransactionEntity::class,
            parentColumns = ["id"],
            childColumns = ["transaction_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionBookmarkEntity (

    @PrimaryKey
    @ColumnInfo(name = "transaction_id")
    val transactionId: String,

    @ColumnInfo(name = "bookmarked_at")
    val bookmarkedAt: Long
)