package com.cobasendiri.kasirmudahkmp.ui.features.tab.di

import com.arkivanov.decompose.ComponentContext
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component.DefaultShopTabComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component.ShopTabComponent
import org.koin.dsl.module

val tabModule = module {
    factory<ShopTabComponent> { (ctx: ComponentContext, onNavigateToReceiptDraft: () -> Unit) ->
        DefaultShopTabComponent(
            componentContext = ctx,
            getProductLisUseCase = get(),
            getCartListUseCase = get(),
            addProductUseCase = get(),
            updateProductUseCase = get(),
            updateProductColorCodeUseCase = get(),
            getTotalCartAmountUseCase = get(),
            incrementProductUseCase = get(),
            decrementProductUseCase = get(),
            clearCartUseCase = get(),
            deleteProductUseCase = get(),
            onNavigateToReceiptDraft = onNavigateToReceiptDraft,
        )
    }
}