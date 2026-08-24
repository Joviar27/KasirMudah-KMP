package com.cobasendiri.kasirmudahkmp.core.data.room.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.ProductEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.CartEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.CartDao
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.ProductDao

@Database(
    entities = [
        ProductEntity::class,
        CartEntity::class
    ],
    version = 1,
    exportSchema = false
)
@ConstructedBy(CharacterDatabaseConstructor::class)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CharacterDatabaseConstructor: RoomDatabaseConstructor<ShopDatabase>{
    override fun initialize(): ShopDatabase
}