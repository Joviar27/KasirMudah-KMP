package com.cobasendiri.kasirmudahkmp.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
actual fun SystemAppearance(isLight: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowInsetsControllerCompat(window, view)

            controller.isAppearanceLightStatusBars = isLight
            controller.isAppearanceLightNavigationBars = isLight
        }
    }
}