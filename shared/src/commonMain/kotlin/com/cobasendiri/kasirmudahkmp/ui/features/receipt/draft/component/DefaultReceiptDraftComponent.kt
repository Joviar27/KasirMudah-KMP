package com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.AddTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponent
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DefaultReceiptDraftComponent(
    componentContext: ComponentContext,
    private val appScope: CoroutineScope,
    private val getReceiptItemsUseCase: GetReceiptItemsUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToHistory: () -> Unit
): BaseComponent(), ReceiptDraftComponent, ComponentContext by componentContext {

    private val retained = instanceKeeper.getOrCreate { RetainedScope() }
    private val scope = retained.scope

    private val _state = MutableValue(ReceiptDraftComponent.ReceiptDraftState())
    override val state: Value<ReceiptDraftComponent.ReceiptDraftState> = _state

    init {
        getReceiptItems()
        getReceiptTotalAmount()
    }

    override fun getReceiptItems(){
        scope.launch {
            getReceiptItemsUseCase.invoke().handleResult{ items ->
                _state.update { it.copy(transactionShopItems = items) }
            }
        }
    }

    override fun getReceiptTotalAmount(){
        scope.launch {
            getTotalCartAmountUseCase.invoke().firstOrNull()?.handleResult{ total ->
                _state.update { it.copy(totalTransaction = total ?: 0) }
            }
        }
    }

    override fun saveNewTransaction(){
        scope.launch {
            addTransactionUseCase.invoke().handleResult{
                onNavigateToHistory()
                clearCart()
            }
        }
    }

    override fun clearCart(){
        appScope.launch {
            clearCartUseCase.invoke()
        }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }

    override fun onNavigateBack() {
        onNavigateBack.invoke()
    }

    fun onNavigateToHistory(){
        onNavigateToHistory.invoke()
    }

    private class RetainedScope : InstanceKeeper.Instance {
        val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        override fun onDestroy() {
            scope.cancel()
        }
    }
}
