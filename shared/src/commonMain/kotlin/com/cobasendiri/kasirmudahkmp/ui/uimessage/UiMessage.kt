package com.cobasendiri.kasirmudahkmp.ui.uimessage

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource as ComposeStringResource

sealed interface UiMessage {
    val id: Long
    val type: UiMessageType

    data class DynamicString(
        override val id: Long,
        override val type: UiMessageType,
        val value: String
    ) : UiMessage

    data class StringResource(
        override val id: Long,
        override val type: UiMessageType,
        val resource: ComposeStringResource
    ) : UiMessage

    fun getMessageId(): Long = id

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resource)
    }
}