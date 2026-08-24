package com.cobasendiri.kasirmudahkmp.ui.features

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.detail.component.ReceiptDetailComponent
import com.cobasendiri.kasirmudahkmp.ui.features.receipt.draft.component.ReceiptDraftComponent
import com.cobasendiri.kasirmudahkmp.ui.features.tab.MainComponent
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.component.get

class RootComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, KoinComponent {

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
                        rootNavigation.pushNew(RootConfig.ReceiptDraftScreen)
                    },
                    onNavigateToTransactionDetail = {
                        rootNavigation.pushNew(RootConfig.ReceiptDetailScreen(it))
                    }
                )
            )
            is RootConfig.ReceiptDraftScreen -> Child.ReceiptDraftChild(
                component = get<ReceiptDraftComponent> {
                    parametersOf(context, {
                        rootNavigation.pop()
                    })
                }
            )
            is RootConfig.ReceiptDetailScreen -> Child.ReceiptDetailChild(
                component = get<ReceiptDetailComponent>{
                    parametersOf(context, config.transactionId, {
                        rootNavigation.pop()
                    })
                }
            )
        }
    }

    sealed interface Child{
        class MainTabChild(val component: MainComponent): Child
        class ReceiptDraftChild(val component: ReceiptDraftComponent): Child
        class ReceiptDetailChild(val component: ReceiptDetailComponent): Child
    }
}