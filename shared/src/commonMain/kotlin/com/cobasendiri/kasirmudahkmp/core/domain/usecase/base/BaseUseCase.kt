package com.cobasendiri.kasirmudahkmp.core.domain.usecase.base

import co.touchlab.kermit.Logger

abstract class BaseUseCase {

    abstract val log: Logger

    fun logWarning(message: String){
        log.w { message }
    }

    fun logInfo(message: String){
        log.i { message }
    }
}