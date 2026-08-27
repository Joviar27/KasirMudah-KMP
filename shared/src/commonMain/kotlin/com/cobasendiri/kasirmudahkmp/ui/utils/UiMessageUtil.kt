package com.cobasendiri.kasirmudahkmp.ui.utils

import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessageType
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.Uuid

object UiMessageUtil {

    fun String.asUiMessage(
        type: UiMessageType = UiMessageType.INFORMATION
    ): UiMessage {
        val getRandomId = getRandomUuid()
        return UiMessage.DynamicString(getRandomId, type, this)
    }

    fun StringResource.asUiMessage(
        type: UiMessageType = UiMessageType.INFORMATION
    ): UiMessage{
        val getRandomId = getRandomUuid()
        return UiMessage.StringResource(getRandomId, type, this)
    }

    private fun getRandomUuid(): Long{
        return Uuid.random().toLongs { mostSignificantBits, _ ->
            mostSignificantBits
        }
    }
}