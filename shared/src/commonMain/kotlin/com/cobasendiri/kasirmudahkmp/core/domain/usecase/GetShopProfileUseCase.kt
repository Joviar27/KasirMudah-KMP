package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetShopProfileUseCase(
    private val profileRepository: IProfileRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetShopProfileUseCase")

    fun invoke(): Flow<Result<ShopProfile>>{
        return profileRepository.getShopProfile().map {
            logInfo("Shop profile: $it")
            Result.Success(it)
        }.catch {
            Result.Error(it)
        }
    }
}