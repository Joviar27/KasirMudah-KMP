package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.data.gallery.AppSettingHandler
import com.cobasendiri.kasirmudahkmp.core.domain.Result

class OpenAppSettingUseCase(
    private val appSettingHandler: AppSettingHandler
) {
    fun invoke(): Result<Unit>{
        return try {
            val result = appSettingHandler.openAppSettings()
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}