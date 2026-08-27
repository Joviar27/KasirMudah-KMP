package com.cobasendiri.kasirmudahkmp.core.data.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(
    tableName = "carts",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,

    val count: Int
)