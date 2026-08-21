package com.cobasendiri.kasirmudahkmp.core.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val price: Long,

    @ColumnInfo(name = "color_code")
    val colorCode: Long
)