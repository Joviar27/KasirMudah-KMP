package com.cobasendiri.kasirmudahkmp.ui.utils

import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DecomposeUtils {
    fun <T : Any> Value<T>.toStateFlow(lifecycle: Lifecycle): StateFlow<T> {
        val state = MutableStateFlow(value)
        val cancellation = subscribe { state.value = it }
        lifecycle.doOnDestroy { cancellation.cancel() }
        return state.asStateFlow()
    }
}