package com.cobasendiri.kasirmudahkmp.features.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cobasendiri.kasirmudahkmp.features.history.HistoryTabScreen
import com.cobasendiri.kasirmudahkmp.features.shop.HomeTabScreen
import com.cobasendiri.kasirmudahkmp.features.profile.ProfileTabScreen
import com.cobasendiri.kasirmudahkmp.features.view.FloatingNavigationBar
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.ic_discount
import kasirmudah_kmp.shared.generated.resources.ic_history
import kasirmudah_kmp.shared.generated.resources.ic_profile

@Composable
fun MainTabScreen(
    component: MainComponent
) {
    val tabPageState by component.tabPages.subscribeAsState()
    val activeIndex = tabPageState.selectedIndex

    val mainNavItems = listOf(
        FloatingNavItem(0, Res.drawable.ic_discount),
        FloatingNavItem(1, Res.drawable.ic_history),
        FloatingNavItem(2, Res.drawable.ic_profile),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(Modifier.fillMaxWidth()
                .padding(bottom = 24.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                FloatingNavigationBar(
                    mainNavItems,
                    activeIndex
                ) { route ->
                    component.selectTab(route)
                }
            }
        }
    ) { innerPadding ->
        ChildPages(
            pages = component.tabPages,
            onPageSelected = { index -> component.selectTab(index)},
            scrollAnimation = PagesScrollAnimation.Disabled
        ){ _, child ->
            when(child){
                is MainComponent.TabChild.HomeChild -> HomeTabScreen()
                is MainComponent.TabChild.HistoryChild -> HistoryTabScreen()
                is MainComponent.TabChild.ProfileChild -> ProfileTabScreen()
            }
        }
    }
}