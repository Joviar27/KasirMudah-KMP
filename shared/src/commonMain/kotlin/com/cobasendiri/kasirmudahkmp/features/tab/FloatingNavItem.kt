package com.cobasendiri.kasirmudahkmp.features.tab

import org.jetbrains.compose.resources.DrawableResource

data class FloatingNavItem<T: Any>(
    val route: T,
    val iconRes: DrawableResource,
)