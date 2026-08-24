package com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component

import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponentInterface
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface ReceiptDraftComponent: BaseComponentInterface{

    val state: Value<ReceiptDraftState>

    fun getReceiptItems()
    fun getReceiptTotalAmount()
    fun saveNewTransaction()
    fun clearCart()
    fun onNavigateBack()

    data class ReceiptDraftState(
        val shopName: String = "",
        val transactionShopItems: List<TransactionItemInfo> = listOf(),
        val totalTransaction: Long = 0L,
        val uiMessage: UiMessage? = null
    )
}

