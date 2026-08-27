package com.cobasendiri.kasirmudahkmp.core.data.repository

import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapToEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.ProductDao
import com.cobasendiri.kasirmudahkmp.core.data.util.IdGenerator
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductDraft
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): IProductRepository {

    override fun getAllProducts(searchQuery: String): Flow<List<ProductInfo>?> {
        return productDao.getAllProducts()
            .mapExceptionFlow { products ->
                val filtered = if(searchQuery.isNotEmpty()){
                    products.filter { it.productEntity.name.contains(searchQuery, true) }
                }else {
                    products
                }.sortedByDescending {
                    it.productEntity.id
                }
                filtered.mapListToDomain()
            }.flowOn(ioDispatcher)
    }

    override suspend fun addNewProduct(
        newProduct: ProductDraft
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            productDao.addProduct(
                newProduct.copy(
                    id = IdGenerator.generateProductId()
                ).mapToEntity()
            )
        }
    }

    override suspend fun updateProduct(
        productDraft: ProductDraft
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            productDao.updateProduct(productDraft.mapToEntity())
        }
    }

    override suspend fun updateProductColorCode(
        productId: String,
        newColor: Long
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            productDao.updateProductColorCode(productId, newColor)
        }
    }

    override suspend fun deleteProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            productDao.deleteProduct(productId)
        }
    }
}

