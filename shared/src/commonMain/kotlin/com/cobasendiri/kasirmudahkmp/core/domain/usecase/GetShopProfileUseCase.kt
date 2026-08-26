package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetShopProfileUseCase(
    private val profileRepository: IProfileRepository
) {
    fun invoke(): Flow<Result<ShopProfile>>{
        return profileRepository.getShopProfile().map {
            Result.Success(it)
        }.catch {
            Result.Error(it)
        }
    }
}