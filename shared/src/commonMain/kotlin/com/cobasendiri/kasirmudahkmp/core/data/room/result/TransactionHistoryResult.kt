package com.cobasendiri.kasirmudahkmp.core.data.room.result

data class TransactionHistoryResult(
    val id: String,
    val name: String,
    val total: Long,
    val createdAt: Long,
    val isBookmarked: Boolean
)