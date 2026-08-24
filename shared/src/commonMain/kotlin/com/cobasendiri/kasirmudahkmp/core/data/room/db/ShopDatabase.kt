package com.cobasendiri.kasirmudahkmp.core.data.room.db

import androidx.room3.ColumnTypeConverters
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.cobasendiri.kasirmudahkmp.core.data.room.converters.TransactionItemConverters
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.ProductEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.CartEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.CartDao
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.ProductDao
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.TransactionDao
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionBookmarkEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntity

@Database(
    entities = [
        ProductEntity::class,
        CartEntity::class,
        TransactionEntity::class,
        TransactionBookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
@ColumnTypeConverters(TransactionItemConverters::class)
@ConstructedBy(ShopDatabaseConstructor::class)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun transactionDao(): TransactionDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ShopDatabaseConstructor: RoomDatabaseConstructor<ShopDatabase>{
    override fun initialize(): ShopDatabase
}