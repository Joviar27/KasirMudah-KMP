package com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component.ReceiptDetailComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.view.ReceiptTopBar
import com.cobasendiri.kasirmudahkmp.ui.view.alertbar.UiMessageBar
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ReceiptDetailScreen(
    component: ReceiptDetailComponent,
) {

    val state by component.state.subscribeAsState()

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L.milliseconds)
            component.uiMessageShown()
        }
    }

    Scaffold(
        topBar = {
            Box{
                ReceiptTopBar(
                    showMenuIcon = true,
                    isBookmarked = state.isBookmarked,
                    onUpdateBookmark = {
                        component.updateBookmark(state.transactionId)
                    },
                    onDelete = {
                        component.showConfirmDeleteDialog(state.transactionId)
                    },
                    onNavigateBack = {
                        component.onNavigateBack()
                    }
                )
                AlertBarAnimatedVisibility(state.uiMessage != null) {
                    state.uiMessage?.let {
                        UiMessageBar(it)
                    }
                }
            }
        }
    ) { innerPadding ->
        ReceiptDetailContent(
            innerPadding,
            state
        ) { event ->
            when (event) {
                is ReceiptDetailEvent.OnDismissConfirmDeleteDialog -> {
                    component.dismissConfirmDeleteDialog()
                }
                is ReceiptDetailEvent.OnDelete -> {
                    component.deleteTransaction(event.transactionId)
                }
                is ReceiptDetailEvent.OnDownload -> {
                    component.downloadReceipt(event.receiptBitmap, event.fileName)
                }
                is ReceiptDetailEvent.OnDismissGalleryPermissionDialog ->{
                    component.dismissGalleryPermissionDialog()
                }
                is ReceiptDetailEvent.OnOpenAppSetting ->{
                    component.openAppSetting()
                    component.dismissGalleryPermissionDialog()
                }
            }
        }
    }
}