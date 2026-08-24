package com.cobasendiri.kasirmudahkmp.core.data.room.converters

import androidx.room3.ColumnTypeConverter
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntityItem
import kotlinx.serialization.json.Json

class TransactionItemConverters {

    @ColumnTypeConverter
    fun fromRecordedItemList(value: List<TransactionEntityItem>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @ColumnTypeConverter
    fun toRecordedItemList(value: String?): List<TransactionEntityItem>? {
        return value?.let { Json.decodeFromString(it) }
    }
}