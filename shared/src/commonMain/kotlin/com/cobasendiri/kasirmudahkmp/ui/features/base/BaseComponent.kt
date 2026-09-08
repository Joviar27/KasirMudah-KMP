package com.cobasendiri.kasirmudahkmp.ui.features.base

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudahkmp.ui.utils.ErrorMessageMapper.asUiMessage

abstract class BaseComponent: BaseComponentInterface{

    protected fun <T> Result<T>.handleResult(
        onFailed: ((Throwable) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null
    ){
        when(this){
            is Result.Success -> onSuccess?.invoke(this.data)
            is Result.Error -> {
                onFailed?.invoke(this.error) ?: run {
                    val uiMessage = this.error.asUiMessage()
                    showUiMessage(uiMessage)
                }
            }
        }
    }
}