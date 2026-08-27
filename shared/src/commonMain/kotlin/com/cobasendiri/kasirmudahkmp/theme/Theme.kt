package com.cobasendiri.kasirmudahkmp.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
expect fun SystemAppearance(isLight: Boolean)

@Composable
fun KasirMudahTheme(
    isLightSystemBar: Boolean = true,
    content: @Composable () -> Unit,
) {
    SystemAppearance(isLight = isLightSystemBar)

    MaterialTheme(
        typography = kasirMudahTypography(),
        content = content
    )
}