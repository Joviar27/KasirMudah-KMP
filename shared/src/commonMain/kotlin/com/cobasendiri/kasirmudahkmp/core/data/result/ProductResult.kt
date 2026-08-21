package com.cobasendiri.kasirmudahkmp.core.data.result

import androidx.room3.Embedded
import com.cobasendiri.kasirmudahkmp.core.data.entity.ProductEntity

data class ProductResult(
    @Embedded
    val productEntity: ProductEntity,
    val count: Int
)