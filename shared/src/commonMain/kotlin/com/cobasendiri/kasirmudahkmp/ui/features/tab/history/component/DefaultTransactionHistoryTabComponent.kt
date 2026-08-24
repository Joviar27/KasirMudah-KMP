package com.cobasendiri.kasirmudahkmp.ui.features.tab.history.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.cobasendiri.kasirmudahkmp.core.domain.filter.DateFilter
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetBookmarkedTransactionUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTransactionHistoryUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateTransactionNameUseCase
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.history.TransactionFilter
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudahkmp.ui.utils.DecomposeUtils.toStateFlow
import com.cobasendiri.kasirmudahkmp.ui.utils.UiMessageUtil.asUiMessage
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.success_delete_transaction
import kasirmudah_kmp.shared.generated.resources.success_new_bookmark
import kasirmudah_kmp.shared.generated.resources.success_remove_bookmark
import kasirmudah_kmp.shared.generated.resources.success_update_transaction_name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultTransactionHistoryTabComponent(
    componentContext: ComponentContext,
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase,
    private val getBookmarkedTransactionUseCase: GetBookmarkedTransactionUseCase,
    private val updateTransactionBookmarkUseCase: UpdateTransactionBookmarkUseCase,
    private val deleteTransactionHistoryUseCase: DeleteTransactionHistoryUseCase,
    private val updateTransactionNameUseCase: UpdateTransactionNameUseCase,
    private val onNavigateToDetail: (String) -> Unit
): BaseComponent(), TransactionHistoryTabComponent, ComponentContext by componentContext{

    private val retained = instanceKeeper.getOrCreate { RetainedScope() }
    private val scope = retained.scope

    private val _state = MutableValue(TransactionHistoryTabComponent.TransactionHistoryTabState())
    override val state: Value<TransactionHistoryTabComponent.TransactionHistoryTabState> = _state

    init {
        getTransactionHistory()
    }

    override fun updateFilter(newFilter: TransactionFilter) {
        _state.update {
            it.copy(
                filter = newFilter,
                showEmptyListView = false
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTransactionHistory(){
        scope.launch {
            _state.toStateFlow(lifecycle).map { it.filter }
                .flatMapLatest { filter ->
                    if(filter == TransactionFilter.FILTER_BOOKMARKED){
                        getBookmarkedTransactionUseCase.invoke()
                    }else{
                        getTransactionHistoryUseCase.invoke(mapFilter(filter))
                    }
                }.collect { result ->
                    result.handleResult{ transactions ->
                        _state.update { it.copy(
                            transactionList = transactions,
                            showEmptyListView = transactions.isEmpty()
                        ) }
                    }
                }
        }
    }

    override fun deleteTransaction(transactionId: String){
        scope.launch {
            deleteTransactionHistoryUseCase.invoke(transactionId).handleResult{
                showUiMessage(Res.string.success_delete_transaction.asUiMessage(UiMessageType.SUCCESS))
                dismissConfirmDeleteDialog()
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

    override fun updateTransactionName(transactionId: String, newName: String){
        scope.launch {
            updateTransactionNameUseCase.invoke(transactionId, newName).handleResult{
                dismissEditDialog()

                val stringRes = Res.string.success_update_transaction_name
                showUiMessage(stringRes.asUiMessage(UiMessageType.SUCCESS))
            }
        }
    }

    private fun mapFilter(transactionFilter: TransactionFilter): DateFilter{
        return when(transactionFilter){
            TransactionFilter.FILTER_TODAY -> DateFilter.TODAY
            TransactionFilter.FILTER_LAST_WEEK -> DateFilter.LAST_WEEK
            TransactionFilter.FILTER_LAST_MONTH -> DateFilter.LAST_MONTH
            else -> DateFilter.ALL_TIME
        }
    }

    override fun showConfirmDeleteDialog(transactionId: String){
        _state.update { it.copy(showConfirmDeleteDialog = transactionId) }
    }

    override fun dismissConfirmDeleteDialog(){
        _state.update { it.copy(showConfirmDeleteDialog = null) }
    }

    override fun showEditDialog(transactionId: String, name: String){
        _state.update { it.copy(showEditDialog = Pair(transactionId, name)) }
    }

    override fun dismissEditDialog(){
        _state.update { it.copy(showEditDialog = null) }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }

    override fun onNavigateDetail(id: String) {
        onNavigateToDetail.invoke(id)
    }

    private class RetainedScope : InstanceKeeper.Instance {
        val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        override fun onDestroy() {
            scope.cancel()
        }
    }
}