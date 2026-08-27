package com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component

import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.core.domain.model.Product
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponentInterface
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.ShopFilter
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface ShopTabComponent: BaseComponentInterface {

    val state: Value<ShopTabState>

    fun updateQuery(newQuery: String)
    fun updateFilter(newFilter: ShopFilter)
    fun loadProductList()
    fun getTotalCartAmount()
    fun getShopProfile()
    fun incrementProduct(productId: String)
    fun decrementProduct(productId: String)
    fun clearCart()
    fun addNewProduct(productDraft: ProductDraft)
    fun updateProduct(productDraft: ProductDraft)
    fun updateProductColorCode(productId: String, newColor: Long)
    fun deleteProduct(productId: String)
    fun showAddProductDialog()
    fun showEditProductDialog(product: Product)
    fun dismissProductDetailDialog()
    fun showConfirmDeleteDialog(productId: String)
    fun dismissConfirmDeleteDialog()
    fun onNavigateToReceiptDraft()
    fun showUnavailableDialog()
    fun dismissUnavailableDialog()

    data class ShopTabState(
        val shopName: String = "",
        val date: Long = 0L,
        val totalAmount: Long = 0L,
        val filter: ShopFilter = ShopFilter.FILTER_ALL,
        val shopItemList: List<ProductInfo> = listOf(),
        val searchQuery: String = "",
        val uiMessage: UiMessage? = null,
        val isFloatingActionVisible: Boolean = false,
        val showEditProductDialog: Product? = null,
        val showAddProductDialog: Boolean = false,
        val showConfirmDeleteDialog: String? = null,
        val showUnavailableDialog: Boolean = false,
        val showEmptyListView: Boolean = false
    )
}