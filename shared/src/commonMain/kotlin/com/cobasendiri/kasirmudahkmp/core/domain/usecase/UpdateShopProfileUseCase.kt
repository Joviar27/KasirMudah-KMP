package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository

class UpdateShopProfileUseCase(
    private val profileRepository: IProfileRepository
) {
    suspend fun invoke(shopProfile: ShopProfile): Result<Unit> {
        return try {
            val isNameValid = shopProfile.let {
                it.shopName.isNotBlank() && it.shopName.firstOrNull()?.isWhitespace() == false
            }
            if(!isNameValid){
                throw KasirMudahException.InvalidInputError
            }

            val result = profileRepository.saveShopProfile(
                shopProfile.shopName,
                shopProfile.shopImage
            )
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}