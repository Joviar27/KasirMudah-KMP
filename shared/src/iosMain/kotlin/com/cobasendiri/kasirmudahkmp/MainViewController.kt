package com.cobasendiri.kasirmudahkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.cobasendiri.kasirmudahkmp.features.App
import com.cobasendiri.kasirmudahkmp.features.RootComponent

fun MainViewController() = ComposeUIViewController {

    val lifecycle = LifecycleRegistry()
    val rootComponent = RootComponent(DefaultComponentContext(lifecycle))

    App(rootComponent)
}