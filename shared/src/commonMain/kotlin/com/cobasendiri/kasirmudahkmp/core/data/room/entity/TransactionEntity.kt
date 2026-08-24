package com.cobasendiri.kasirmudahkmp.core.data.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,

    val name: String,

    @ColumnInfo(name = "shop_name")
    val shopName: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    val items: List<TransactionEntityItem>,

    val total: Long
)

@Serializable
data class TransactionEntityItem(
    val name: String,
    val count: Int,
    val total: Long
)