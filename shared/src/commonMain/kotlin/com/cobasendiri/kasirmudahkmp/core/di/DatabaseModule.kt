package com.cobasendiri.kasirmudahkmp.core.di

import com.cobasendiri.kasirmudahkmp.core.data.room.ProductDao
import com.cobasendiri.kasirmudahkmp.core.data.room.CartDao
import com.cobasendiri.kasirmudahkmp.core.data.room.DatabaseBuilder
import com.cobasendiri.kasirmudahkmp.core.data.room.ShopDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<ShopDatabase> { get<DatabaseBuilder>().getDatabase() }

    single<ProductDao> { get<ShopDatabase>().productDao() }
    single<CartDao> { get<ShopDatabase>().cartDao() }
}