package com.cobasendiri.kasirmudahkmp.core.di

import com.cobasendiri.kasirmudahkmp.core.data.repository.ProductRepository
import com.cobasendiri.kasirmudahkmp.core.data.repository.CartRepository
import com.cobasendiri.kasirmudahkmp.core.data.repository.TransactionRepository
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ITransactionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IProductRepository> { ProductRepository(get())}

    single<ICartRepository> { CartRepository(get()) }

    single<ITransactionRepository> { TransactionRepository(get()) }
}