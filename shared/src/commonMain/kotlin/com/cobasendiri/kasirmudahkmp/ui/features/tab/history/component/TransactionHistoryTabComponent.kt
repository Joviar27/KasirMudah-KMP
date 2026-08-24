package com.cobasendiri.kasirmudahkmp.ui.features.tab.history.component

import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponentInterface
import com.cobasendiri.kasirmudahkmp.ui.features.tab.history.TransactionFilter
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface TransactionHistoryTabComponent: BaseComponentInterface {

    val state: Value<TransactionHistoryTabState>

    fun updateFilter(newFilter: TransactionFilter)
    fun getTransactionHistory()
    fun deleteTransaction(transactionId: String)
    fun updateBookmark(transactionId: String)
    fun updateTransactionName(transactionId: String, newName: String)
    fun showConfirmDeleteDialog(transactionId: String)
    fun dismissConfirmDeleteDialog()
    fun showEditDialog(transactionId: String, name: String)
    fun dismissEditDialog()
    fun onNavigateDetail(id: String)

    data class TransactionHistoryTabState(
        val filter: TransactionFilter = TransactionFilter.FILTER_ALL,
        val transactionList: List<TransactionHistory> = listOf(),
        val uiMessage: UiMessage? = null,
        val showConfirmDeleteDialog: String? = null,
        val showEditDialog: Pair<String, String>? = null,
        val showEmptyListView: Boolean = false
    )
}