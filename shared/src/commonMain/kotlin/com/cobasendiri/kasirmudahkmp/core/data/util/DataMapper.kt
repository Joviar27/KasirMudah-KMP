package com.cobasendiri.kasirmudahkmp.core.data.util

import com.cobasendiri.kasirmudahkmp.core.data.room.entity.ProductEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.TransactionEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.result.ProductResult
import com.cobasendiri.kasirmudahkmp.core.data.room.result.TransactionHistoryResult
import com.cobasendiri.kasirmudahkmp.core.data.room.result.TransactionItemResult
import com.cobasendiri.kasirmudahkmp.core.domain.model.Product
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionHistory
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionReceipt

object DataMapper {

    fun List<ProductResult>.mapListToDomain(): List<ProductInfo>{
        return this.map { productResults ->
            productResults.mapToDomain()
        }
    }

    fun ProductResult.mapToDomain(): ProductInfo{
        return ProductInfo(
            product = this.productEntity.mapToDomain(),
            count = this.count
        )
    }

    fun ProductEntity.mapToDomain(): Product{
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            colorCode = this.colorCode
        )
    }

    fun Product.mapToEntity(): ProductEntity{
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            colorCode = this.colorCode
        )
    }

    //Always wrap with try catch
    fun ProductDraft.mapToEntity(): ProductEntity{
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price.toLong(),
            colorCode = this.colorCode
        )
    }

    fun List<TransactionItemResult>.mapToDomain(): List<TransactionItemInfo>{
        return this.map {
            TransactionItemInfo(
                name = it.name,
                count = it.count,
                itemTotal = it.total
            )
        }
    }

    fun List<TransactionHistoryResult>.mapTransactionHistoryToDomain(): List<TransactionHistory>{
        return this.map {
            TransactionHistory(
                id = it.id,
                name = it.name,
                total = it.total,
                createdAt = it.createdAt,
                isBookmarked = it.isBookmarked
            )
        }
    }

    fun TransactionEntity.mapToTransactionReceipt(): TransactionReceipt {
        return TransactionReceipt(
            id = this.id,
            name = this.name,
            shopName = this.shopName,
            createdAt = this.createdAt,
            shopItems = this.items.map {
                TransactionItemInfo(
                    name = it.name,
                    count = it.count,
                    itemTotal = it.total
                )
            },
            transactionTotal = this.total
        )
    }
}