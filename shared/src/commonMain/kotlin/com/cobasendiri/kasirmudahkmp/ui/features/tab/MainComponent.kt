package com.cobasendiri.kasirmudahkmp.ui.features.tab

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.ui.features.tab.shop.component.ShopTabComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MainComponent(
    componentContext: ComponentContext,
    val onNavigateToReceiptDraft: () -> Unit
): ComponentContext by componentContext, KoinComponent {

    private val tabNavigation = PagesNavigation<MainTabConfig>()

    val tabPages: Value<ChildPages<MainTabConfig, TabChild>> = childPages(
        source = tabNavigation,
        serializer = MainTabConfig.serializer(),
        initialPages = {
            Pages(
                items = listOf(MainTabConfig.Home, MainTabConfig.History, MainTabConfig.Profile),
                selectedIndex = 0
            )
        },
        childFactory = ::createChild
    )

    private fun createChild(config: MainTabConfig, context: ComponentContext): TabChild{
        return when(config){
            MainTabConfig.Home -> TabChild.ShopChild(
                component = get<ShopTabComponent>{
                    parametersOf(context, onNavigateToReceiptDraft)
                }
            )
            MainTabConfig.History -> TabChild.HistoryChild()
            MainTabConfig.Profile -> TabChild.ProfileChild()
        }
    }

    fun selectTab(index: Int){
        tabNavigation.select(index)
    }

    sealed interface TabChild{
        class ShopChild(val component: ShopTabComponent): TabChild
        class HistoryChild(): TabChild
        class ProfileChild(): TabChild
    }
}