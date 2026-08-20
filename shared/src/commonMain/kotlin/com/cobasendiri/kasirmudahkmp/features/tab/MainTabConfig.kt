package com.cobasendiri.kasirmudahkmp.features.tab

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainTabConfig {

    @Serializable
    data object Home: MainTabConfig

    @Serializable
    data object History: MainTabConfig

    @Serializable
    data object Profile: MainTabConfig
}