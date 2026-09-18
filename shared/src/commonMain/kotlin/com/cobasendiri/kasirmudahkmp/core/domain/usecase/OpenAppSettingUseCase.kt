package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.data.gallery.AppSettingHandler
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class OpenAppSettingUseCase(
    private val appSettingHandler: AppSettingHandler
): BaseUseCase() {

    override val log: Logger = Logger.withTag("OpenAppSettingUseCase")

    fun invoke(): Result<Unit>{
        return try {
            val result = appSettingHandler.openAppSettings()
            logInfo("Opening app setting")
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}