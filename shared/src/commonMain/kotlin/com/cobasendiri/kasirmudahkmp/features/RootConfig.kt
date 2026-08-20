package com.cobasendiri.kasirmudahkmp.features

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data object MainTabScreen: RootConfig
}