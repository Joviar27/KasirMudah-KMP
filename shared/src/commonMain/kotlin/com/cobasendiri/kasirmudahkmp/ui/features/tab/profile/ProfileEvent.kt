package com.cobasendiri.kasirmudahkmp.ui.features.tab.profile

import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile

interface ProfileEvent {

    data class OnShowEditProfileDialog(
        val shopProfile: ShopProfile
    ): ProfileEvent

    data object OnDismissEditProfileDialog: ProfileEvent

    data class OnEditProfile(
        val newShopProfile: ShopProfile
    ): ProfileEvent

    data object OnShowUnavailableDialog: ProfileEvent

    data object OnDismissUnavailableDialog: ProfileEvent
}