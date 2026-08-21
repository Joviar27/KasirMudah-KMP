package com.cobasendiri.kasirmudahkmp.ui.view.navbar

import org.jetbrains.compose.resources.DrawableResource

data class FloatingNavItem<T: Any>(
    val route: T,
    val iconRes: DrawableResource,
)