package com.cobasendiri.kasirmudahkmp.ui.features.receipt.di

import com.arkivanov.decompose.ComponentContext
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component.DefaultReceiptDetailComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component.ReceiptDetailComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component.DefaultReceiptDraftComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component.ReceiptDraftComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val receiptModule = module {

    single<CoroutineScope>(named("AppScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    factory<ReceiptDetailComponent> {
        (ctx: ComponentContext, transactionId: String, onNavigateBack: () -> Unit) ->

        DefaultReceiptDetailComponent(
            transactionId = transactionId,
            componentContext = ctx,
            getTransactionUseCase = get(),
            getIsTransactionBookmarkedUseCase = get(),
            updateTransactionBookmarkUseCase = get(),
            deleteTransactionHistoryUseCase = get(),
            saveReceiptImageUseCase = get(),
            onNavigateBack = onNavigateBack,
        )
    }

    factory<ReceiptDraftComponent> {
        (ctx: ComponentContext, onNavigateBack: () -> Unit, onNavigateToHistory: () -> Unit) ->

        DefaultReceiptDraftComponent(
            componentContext = ctx,
            appScope = get(named("AppScope")),
            getReceiptItemsUseCase = get(),
            getTotalCartAmountUseCase = get(),
            addTransactionUseCase = get(),
            clearCartUseCase = get(),
            onNavigateBack = onNavigateBack,
            onNavigateToHistory = onNavigateToHistory
        )
    }
}