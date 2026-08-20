package com.cobasendiri.kasirmudahkmp.features

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.cobasendiri.kasirmudahkmp.features.tab.MainComponent
import com.cobasendiri.kasirmudahkmp.features.tab.MainTabScreen
import com.cobasendiri.kasirmudahkmp.theme.KasirMudahTheme

@Composable
fun App(
    rootComponent: RootComponent
) {
    KasirMudahTheme {
        Children(
            stack = rootComponent.childStack,
            animation = stackAnimation(slide())
        ){ target ->
            when(val child = target.instance){
                is RootComponent.Child.MainTabChild -> MainTabScreen(child.component)
            }
        }
    }
}