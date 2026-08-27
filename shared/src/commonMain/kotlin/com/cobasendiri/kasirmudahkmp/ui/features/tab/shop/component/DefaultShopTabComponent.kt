package com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.cobasendiri.kasirmudahkmp.core.domain.model.Product
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.DeleteProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductColorCodeUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateProductUseCase
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.ShopFilter
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudahkmp.ui.utils.DecomposeUtils.toStateFlow
import com.cobasendiri.kasirmudahkmp.ui.utils.UiMessageUtil.asUiMessage
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.succcess_add
import kasirmudah_kmp.shared.generated.resources.success_delete
import kasirmudah_kmp.shared.generated.resources.success_update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Clock

class DefaultShopTabComponent(
    componentContext: ComponentContext,
    private val getProductLisUseCase: GetProductLisUseCase,
    private val getCartListUseCase: GetCartListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val updateProductColorCodeUseCase: UpdateProductColorCodeUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val incrementProductUseCase: IncrementProductUseCase,
    private val decrementProductUseCase: DecrementProductUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getShopProfileUseCase: GetShopProfileUseCase,
    private val onNavigateToReceiptDraft: () -> Unit
): BaseComponent(), ShopTabComponent, ComponentContext by componentContext {

    private val retained = instanceKeeper.getOrCreate { RetainedScope() }
    private val scope = retained.scope

    private val _state = MutableValue(ShopTabComponent.ShopTabState())
    override val state: Value<ShopTabComponent.ShopTabState> = _state

    init {
        loadProductList()
        getTotalCartAmount()
        getShopProfile()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updateQuery(newQuery: String) {
        _state.update {
            it.copy(searchQuery = newQuery)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updateFilter(newFilter: ShopFilter) {
        _state.update {
            it.copy(
                filter = newFilter,
                showEmptyListView = false
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadProductList(){
        scope.launch {
            _state.toStateFlow(lifecycle).map { Pair(it.searchQuery, it.filter) }
                .flatMapLatest { condition ->
                    if(condition.second == ShopFilter.FILTER_CART){
                        getCartListUseCase.invoke(condition.first)
                    }else{
                        getProductLisUseCase.invoke(condition.first)
                    }
                }.collect { result ->
                    result.handleResult { products ->
                        _state.update { it.copy(
                            shopItemList = products,
                            showEmptyListView = products.isEmpty()
                        ) }
                    }
                }
        }
    }

    override fun getTotalCartAmount(){
        scope.launch {
            getTotalCartAmountUseCase.invoke().collect { result ->
                result.handleResult { totalAmount ->
                    _state.update { it.copy(
                        totalAmount = totalAmount ?: 0L,
                        isFloatingActionVisible = totalAmount != null && totalAmount > 0L
                    ) }
                }
            }
        }
    }

    override fun getShopProfile(){
        scope.launch {
            getShopProfileUseCase.invoke().collect { result ->
                result.handleResult{ shopProfile ->
                    _state.update {
                        it.copy(
                            shopName = shopProfile.shopName,
                            date = Clock.System.now().toEpochMilliseconds()/1000
                        )
                    }
                }
            }
        }
    }

    override fun incrementProduct(productId: String){
        scope.launch {
            incrementProductUseCase.invoke(productId).handleResult()
        }
    }

    override fun decrementProduct(productId: String){
        scope.launch {
            decrementProductUseCase.invoke(productId).handleResult()
        }
    }

    override fun clearCart(){
        scope.launch {
            clearCartUseCase.invoke().handleResult()
        }
    }

    override fun addNewProduct(productDraft: ProductDraft){
        scope.launch {
            addProductUseCase.invoke(productDraft).handleResult {
                showUiMessage(Res.string.succcess_add.asUiMessage(UiMessageType.SUCCESS))
                dismissProductDetailDialog()
            }
        }
    }

    override fun updateProduct(productDraft: ProductDraft){
        scope.launch {
            updateProductUseCase.invoke(productDraft).handleResult{
                showUiMessage(Res.string.success_update.asUiMessage(UiMessageType.SUCCESS))
                dismissProductDetailDialog()
            }
        }
    }

    override fun updateProductColorCode(productId: String, newColor: Long){
        scope.launch {
            updateProductColorCodeUseCase.invoke(productId, newColor).handleResult()
        }
    }

    override fun deleteProduct(productId: String){
        scope.launch {
            deleteProductUseCase.invoke(productId).handleResult{
                showUiMessage(Res.string.success_delete.asUiMessage(UiMessageType.SUCCESS))
                dismissConfirmDeleteDialog()
            }
        }
    }

    override fun showAddProductDialog(){
        _state.update {
            it.copy(showAddProductDialog = true)
        }
    }

    override fun showEditProductDialog(product: Product){
        _state.update {
            it.copy(showEditProductDialog = product)
        }
    }

    override fun dismissProductDetailDialog(){
        _state.update {
            it.copy(
                showAddProductDialog = false,
                showEditProductDialog = null
            )
        }
    }

    override fun showConfirmDeleteDialog(productId: String){
        _state.update { it.copy(showConfirmDeleteDialog = productId) }
    }

    override fun dismissConfirmDeleteDialog(){
        _state.update { it.copy(showConfirmDeleteDialog = null) }
    }

    override fun showUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = true)
        }
    }

    override fun dismissUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = false)
        }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }

    override fun onNavigateToReceiptDraft() {
        onNavigateToReceiptDraft.invoke()
    }

    private class RetainedScope : InstanceKeeper.Instance {
        val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        override fun onDestroy() {
            scope.cancel()
        }
    }
}