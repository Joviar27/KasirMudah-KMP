package com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetIsTransactionBookmarkedUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.SaveReceiptImageUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponent
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudahkmp.ui.utils.UiMessageUtil.asUiMessage
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.error_save_receipt
import kasirmudah_kmp.shared.generated.resources.success_new_bookmark
import kasirmudah_kmp.shared.generated.resources.success_remove_bookmark
import kasirmudah_kmp.shared.generated.resources.success_save_receipt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class DefaultReceiptDetailComponent(
    transactionId: String,
    componentContext: ComponentContext,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getIsTransactionBookmarkedUseCase: GetIsTransactionBookmarkedUseCase,
    private val updateTransactionBookmarkUseCase: UpdateTransactionBookmarkUseCase,
    private val deleteTransactionHistoryUseCase: DeleteTransactionHistoryUseCase,
    private val saveReceiptImageUseCase: SaveReceiptImageUseCase,
    private val onNavigateBack: () -> Unit
): BaseComponent(), ReceiptDetailComponent, ComponentContext by componentContext{

    private val retained = instanceKeeper.getOrCreate { RetainedScope() }
    private val scope = retained.scope

    private val _state = MutableValue(ReceiptDetailComponent.ReceiptDetailState())
    override val state: Value<ReceiptDetailComponent.ReceiptDetailState> = _state

    init {
        getTransaction(transactionId)
        getIsBookmarked(transactionId)
    }

    override fun getTransaction(transactionId: String){
        scope.launch {
            getTransactionUseCase.invoke(transactionId).handleResult{ receipt ->
                _state.update {
                    it.copy(
                        transactionId = receipt.id,
                        transactionName = receipt.name,
                        transactionCreatedAt = receipt.createdAt,
                        transactionShopItems = receipt.shopItems,
                        totalTransaction = receipt.transactionTotal,
                        shopName = receipt.shopName,
                        processing = false
                    )
                }
            }
        }
    }

    override fun getIsBookmarked(transactionId: String){
        scope.launch {
            getIsTransactionBookmarkedUseCase.invoke(transactionId).collect { result ->
                result.handleResult{ isBookmarked ->
                    _state.update { it.copy(isBookmarked = isBookmarked) }
                }
            }
        }
    }

    override fun updateBookmark(transactionId: String){
        scope.launch {
            updateTransactionBookmarkUseCase.invoke(transactionId).handleResult{ isBookmarked ->
                val stringRes = if(isBookmarked) {
                    Res.string.success_new_bookmark
                } else {
                    Res.string.success_remove_bookmark
                }
                showUiMessage(stringRes.asUiMessage(UiMessageType.SUCCESS))
            }
        }
    }

    override fun deleteTransaction(transactionId: String){
        scope.launch {
            deleteTransactionHistoryUseCase.invoke(transactionId).handleResult{
                onNavigateBack.invoke()
            }
        }
    }

    override fun showConfirmDeleteDialog(transactionId: String){
        _state.update { it.copy(showConfirmDeleteDialog = transactionId) }
    }

    override fun dismissConfirmDeleteDialog(){
        _state.update { it.copy(showConfirmDeleteDialog = null) }
    }

    override fun downloadReceipt(imageBitmap: ImageBitmap, filename: String) {
        scope.launch {
            _state.update { it.copy(processing = true) }

            saveReceiptImageUseCase.invoke(imageBitmap, filename).handleResult { successSaveReceipt ->
                val uiMessage = if(successSaveReceipt){
                    Res.string.success_save_receipt.asUiMessage(UiMessageType.SUCCESS)
                }else{
                    Res.string.error_save_receipt.asUiMessage(UiMessageType.ERROR)
                }
                showUiMessage(uiMessage)
            }

            //Avoid multiple download
            delay(500.milliseconds)
            _state.update {
                it.copy(processing = false)
            }
        }
    }

    override fun onNavigateBack() {
        onNavigateBack.invoke()
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }

    private class RetainedScope : InstanceKeeper.Instance {
        val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        override fun onDestroy() {
            scope.cancel()
        }
    }
}