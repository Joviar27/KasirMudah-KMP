package com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail

import androidx.compose.ui.graphics.ImageBitmap

interface ReceiptDetailEvent {

    data class OnDelete(
        val transactionId: String
    ): ReceiptDetailEvent

    data object OnDismissConfirmDeleteDialog: ReceiptDetailEvent

    data class OnDownload(
        val receiptBitmap: ImageBitmap,
        val fileName: String
    ): ReceiptDetailEvent
}