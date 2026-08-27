package com.cobasendiri.kasirmudahkmp.core.data.repository

import com.cobasendiri.kasirmudahkmp.core.data.preference.ProfilePreferenceManager
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudahkmp.ui.utils.ImageWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64

class ProfileRepository(
    private val profilePreferenceManager: ProfilePreferenceManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): IProfileRepository {

    override fun getShopProfile(): Flow<ShopProfile> {
         return combine(
             profilePreferenceManager.shopNameFlow,
             profilePreferenceManager.shopImageFlow
         ){ shopName, shopImage ->
             ShopProfile(
                 shopName ?: "",
                 shopImage.takeIf { it?.isNotEmpty() == true }?.let {
                     ImageWrapper(Base64.decode(it))
                 }
             )
         }.mapExceptionFlow().flowOn(ioDispatcher)
    }

    override suspend fun saveShopProfile(
        shopName: String,
        shopImage: ImageWrapper?
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            val shopImageBytes = shopImage?.bytes ?: byteArrayOf()
            profilePreferenceManager.saveShopProfile(
                shopName,
                Base64.encode(shopImageBytes)
            )
        }
    }
}