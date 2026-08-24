package com.cobasendiri.kasirmudahkmp.ui.features

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.cobasendiri.kasirmudahkmp.theme.KasirMudahTheme
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.ReceiptDetailScreen
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.ReceiptDraftScreen
import com.cobasendiri.kasirmudahkmp.ui.features.tab.MainTabScreen

@Composable
fun App(
    rootComponent: RootComponent
) {
    KasirMudahTheme {
        Children(
            stack = rootComponent.childStack,
            animation = stackAnimation(slide())
        ){ target ->
            when(val child = target.instance){
                is RootComponent.Child.MainTabChild -> MainTabScreen(child.component)
                is RootComponent.Child.ReceiptDraftChild -> ReceiptDraftScreen(child.component)
                is RootComponent.Child.ReceiptDetailChild -> ReceiptDetailScreen(child.component)
            }
        }
    }
}