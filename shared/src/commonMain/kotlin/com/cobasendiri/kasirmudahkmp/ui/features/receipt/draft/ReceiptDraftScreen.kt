package com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component.ReceiptDraftComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.view.ReceiptTopBar
import com.cobasendiri.kasirmudahkmp.ui.view.alertbar.UiMessageBar
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ReceiptDraftScreen(
    component: ReceiptDraftComponent
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
            Box {
                ReceiptTopBar(
                    showMenuIcon = false,
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
        ReceiptDraftContent(
            innerPadding = innerPadding,
            state = state
        ) { event ->
            when (event) {
                is ReceiptDraftEvent.OnNavigateBack -> {
                    component.onNavigateBack()
                }

                is ReceiptDraftEvent.OnSave -> {
                    component.saveNewTransaction()
                }
            }
        }
    }
}