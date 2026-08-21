package com.cobasendiri.kasirmudahkmp.ui.features

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.ui.features.tab.MainComponent

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val rootNavigation = StackNavigation<RootConfig>()

    val childStack: Value<ChildStack<RootConfig, Child>> = childStack(
        source = rootNavigation,
        serializer = RootConfig.serializer(),
        initialConfiguration = RootConfig.MainTabScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: RootConfig, context: ComponentContext): Child {
        return when (config) {
            is RootConfig.MainTabScreen -> Child.MainTabChild(
                MainComponent(
                    context,
                    onNavigateToReceiptDraft = {
                        //Navigate to receipt draft screen
                    }
                )
            )
        }
    }

    sealed interface Child{
        class MainTabChild(val component: MainComponent): Child
    }
}