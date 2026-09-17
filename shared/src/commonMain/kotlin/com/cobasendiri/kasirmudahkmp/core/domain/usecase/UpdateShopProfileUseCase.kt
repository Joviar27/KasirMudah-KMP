package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase

class UpdateShopProfileUseCase(
    private val profileRepository: IProfileRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("UpdateShopProfileUseCase")

    suspend fun invoke(shopProfile: ShopProfile): Result<Unit> {
        return try {
            val isNameValid = shopProfile.let {
                it.shopName.isNotBlank() && it.shopName.firstOrNull()?.isWhitespace() == false
            }
            if(!isNameValid){
                logWarning("New name input invalid")
                throw KasirMudahException.InvalidInputError
            }

            val result = profileRepository.saveShopProfile(
                shopProfile.shopName,
                shopProfile.shopImage
            )
            logInfo("Shop profile updated: $shopProfile")
            Result.Success(result)
        }catch (e: KasirMudahException){
            Result.Error(e)
        }
    }
}