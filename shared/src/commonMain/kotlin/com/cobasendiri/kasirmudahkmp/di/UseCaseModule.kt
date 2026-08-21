package com.cobasendiri.kasirmudahkmp.di

import com.cobasendiri.kasirmudahkmp.core.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductColorCodeUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductLisUseCase(get()) }

    factory { GetCartListUseCase(get()) }

    factory { AddProductUseCase(get()) }

    factory { DeleteProductUseCase(get()) }

    factory { UpdateProductUseCase(get()) }

    factory { UpdateProductColorCodeUseCase(get()) }

    factory { IncrementProductUseCase(get()) }

    factory { DecrementProductUseCase(get()) }

    factory { GetTotalCartAmountUseCase(get()) }

    factory { ClearCartUseCase(get()) }
}