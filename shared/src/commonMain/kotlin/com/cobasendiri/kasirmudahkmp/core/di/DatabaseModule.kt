package com.cobasendiri.kasirmudahkmp.core.di

import com.cobasendiri.kasirmudahkmp.core.data.room.dao.ProductDao
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.CartDao
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.TransactionDao
import com.cobasendiri.kasirmudahkmp.core.data.room.db.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.db.ShopDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<ShopDatabase> { get<DatabaseBuilder>().getDatabase() }

    single<ProductDao> { get<ShopDatabase>().productDao() }
    single<CartDao> { get<ShopDatabase>().cartDao() }
    single<TransactionDao> { get<ShopDatabase>().transactionDao() }
}