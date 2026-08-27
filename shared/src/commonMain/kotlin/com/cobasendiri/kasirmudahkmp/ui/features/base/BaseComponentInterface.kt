package com.cobasendiri.kasirmudahkmp.ui.features.base

import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage

interface BaseComponentInterface {
    fun showUiMessage(message: UiMessage){}

    fun uiMessageShown(){}
}