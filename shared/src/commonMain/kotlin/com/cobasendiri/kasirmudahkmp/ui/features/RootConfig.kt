package com.cobasendiri.kasirmudahkmp.ui.features

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data object MainTabScreen: com.cobasendiri.kasirmudahkmp.ui.features.RootConfig
}