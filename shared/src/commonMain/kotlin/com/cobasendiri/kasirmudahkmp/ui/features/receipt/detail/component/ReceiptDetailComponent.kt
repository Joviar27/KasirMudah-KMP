package com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponentInterface
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface ReceiptDetailComponent: BaseComponentInterface {

    val state: Value<ReceiptDetailState>

    fun getTransaction(transactionId: String)
    fun getIsBookmarked(transactionId: String)
    fun updateBookmark(transactionId: String)
    fun deleteTransaction(transactionId: String)
    fun showConfirmDeleteDialog(transactionId: String)
    fun dismissConfirmDeleteDialog()
    fun downloadReceipt(imageBitmap: ImageBitmap, filename: String)
    fun showGalleryPermissionDialog()
    fun dismissGalleryPermissionDialog()
    fun openAppSetting()
    fun onNavigateBack()

    data class ReceiptDetailState(
        val shopName: String = "",
        val transactionName: String = "",
        val transactionId: String = "",
        val transactionCreatedAt: Long = 0L,
        val transactionShopItems: List<TransactionItemInfo> = listOf(),
        val totalTransaction: Long = 0L,
        val isBookmarked: Boolean = false,
        val uiMessage: UiMessage? = null,
        val showConfirmDeleteDialog: String? = null,
        val showGalleryPermissionDialog: Boolean = false,
        val processing: Boolean = true
    )
}