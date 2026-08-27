package com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft

interface ReceiptDraftEvent {

    data object OnNavigateBack: ReceiptDraftEvent

    data object OnSave: ReceiptDraftEvent
}