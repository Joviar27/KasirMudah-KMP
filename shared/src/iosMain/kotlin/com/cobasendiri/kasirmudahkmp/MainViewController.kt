package com.cobasendiri.kasirmudahkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.cobasendiri.kasirmudahkmp.di.initKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {

    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle)
    val rootComponent = object : KoinComponent{
        fun resolve() = get<com.cobasendiri.kasirmudahkmp.ui.features.RootComponent>{ parametersOf(context) }
    }.resolve()

    _root_ide_package_.com.cobasendiri.kasirmudahkmp.ui.features.App(rootComponent)
}