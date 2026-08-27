package com.cobasendiri.kasirmudahkmp.ui.features

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data object MainTabScreen: RootConfig

    @Serializable
    data object ReceiptDraftScreen: RootConfig

    @Serializable
    data class ReceiptDetailScreen(val transactionId: String): RootConfig
}