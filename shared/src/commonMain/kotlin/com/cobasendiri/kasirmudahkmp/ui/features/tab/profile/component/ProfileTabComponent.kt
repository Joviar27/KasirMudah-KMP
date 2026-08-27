package com.cobasendiri.kasirmudahkmp.ui.features.tab.profile.component

import com.arkivanov.decompose.value.Value
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.ui.features.base.BaseComponentInterface
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface ProfileTabComponent: BaseComponentInterface {

    val state: Value<ProfileState>

    fun getShopProfile()
    fun saveShopProfile(shopProfile: ShopProfile)
    fun showEditProfileDialog(shopProfile: ShopProfile)
    fun dismissEditProfileDialog()
    fun showUnavailableDialog()
    fun dismissUnavailableDialog()

    data class ProfileState(
        val shopProfile: ShopProfile = ShopProfile("",null),
        val uiMessage: UiMessage? = null,
        val showEditProfileDialog: ShopProfile? = null,
        val showUnavailableDialog: Boolean = false
    )
}