package com.cobasendiri.kasirmudahkmp.core.data.room.result

import androidx.room3.Embedded
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.ProductEntity

data class ProductResult(
    @Embedded
    val productEntity: ProductEntity,
    val count: Int
)