package com.cobasendiri.kasirmudahkmp.core.domain.repository

import com.cobasendiri.kasirmudahkmp.core.domain.model.ShopProfile
import com.cobasendiri.kasirmudahkmp.ui.utils.ImageWrapper
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    fun getShopProfile(): Flow<ShopProfile>

    suspend fun saveShopProfile(shopName: String, shopImage: ImageWrapper?)
}