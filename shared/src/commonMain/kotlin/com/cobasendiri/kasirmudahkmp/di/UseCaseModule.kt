package com.cobasendiri.kasirmudahkmp.di

import com.cobasendiri.kasirmudahkmp.core.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.AddTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetBookmarkedTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetIsTransactionBookmarkedUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTransactionHistoryUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.OpenAppSettingUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.SaveReceiptImageUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductColorCodeUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateShopProfileUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateTransactionNameUseCase
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

    factory { GetTransactionHistoryUseCase(get()) }

    factory { GetBookmarkedTransactionUseCase(get()) }

    factory { GetTransactionUseCase(get()) }

    factory { GetIsTransactionBookmarkedUseCase(get()) }

    factory { DeleteTransactionHistoryUseCase(get()) }

    factory { UpdateTransactionBookmarkUseCase(get()) }

    factory { UpdateTransactionNameUseCase(get()) }

    factory { GetReceiptItemsUseCase(get()) }

    factory { SaveReceiptImageUseCase(get()) }

    factory { OpenAppSettingUseCase(get()) }

    factory { AddTransactionUseCase(get(), get(), get()) }

    factory { GetShopProfileUseCase(get()) }

    factory { UpdateShopProfileUseCase(get()) }
}