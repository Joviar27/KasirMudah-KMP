package com.cobasendiri.kasirmudahkmp.ui.features.tab.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.UpdateShopProfileUseCase
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponent
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudahkmp.ui.utils.UiMessageUtil.asUiMessage
import kasirmudah_kmp.shared.generated.resources.Res
import kasirmudah_kmp.shared.generated.resources.success_update_shop_profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DefaultProfileTabComponent(
    componentContext: ComponentContext,
    private val getShopProfileUseCase: GetShopProfileUseCase,
    private val updateShopProfileUseCase: UpdateShopProfileUseCase
): BaseComponent(), ProfileTabComponent, ComponentContext by componentContext{

    private val retained = instanceKeeper.getOrCreate { RetainedScope() }
    private val scope = retained.scope

    private val _state = MutableValue(ProfileTabComponent.ProfileState())
    override val state: Value<ProfileTabComponent.ProfileState> = _state

    init {
        getShopProfile()
    }

    override fun getShopProfile(){
        scope.launch {
            getShopProfileUseCase.invoke().collect { result ->
                result.handleResult{ shopProfile ->
                    _state.update { it.copy(shopProfile = shopProfile) }
                }
            }
        }
    }

    override fun saveShopProfile(shopProfile: ShopProfile){
        scope.launch {
            updateShopProfileUseCase.invoke(shopProfile).handleResult{
                dismissEditProfileDialog()
                showUiMessage(Res.string.success_update_shop_profile.asUiMessage(UiMessageType.SUCCESS))
            }
        }
    }

    override fun showEditProfileDialog(shopProfile: ShopProfile){
        _state.update {
            it.copy(showEditProfileDialog = shopProfile)
        }
    }

    override fun dismissEditProfileDialog(){
        _state.update {
            it.copy(showEditProfileDialog = null)
        }
    }

    override fun showUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = true)
        }
    }

    override fun dismissUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = false)
        }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }

    private class RetainedScope : InstanceKeeper.Instance {
        val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        override fun onDestroy() {
            scope.cancel()
        }
    }

}