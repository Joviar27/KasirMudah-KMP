package com.cobasendiri.kasirmudahkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.cobasendiri.kasirmudahkmp.di.initKoin
import com.cobasendiri.kasirmudahkmp.ui.features.App
import com.cobasendiri.kasirmudahkmp.ui.features.RootComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {

    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle)
    val rootComponent = object : KoinComponent{
        fun resolve() = get<RootComponent>{ parametersOf(context) }
    }.resolve()

    App(rootComponent)
}