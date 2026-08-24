package com.cobasendiri.kasirmudahkmp.core.data.repository

import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.CartDao
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CartRepository(
    private val cartDao: CartDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ICartRepository {

    override fun getAllCartProduct(searchQuery: String): Flow<List<ProductInfo>?>{
        return cartDao.getAllCartProducts()
            .mapExceptionFlow{ products ->
                val filtered = if(searchQuery.isNotEmpty()){
                    products.filter { it.productEntity.name.contains(searchQuery, true) }
                }else {
                    products
                }
                filtered.mapListToDomain()
            }.flowOn(ioDispatcher)
    }

    override fun getTotalCartAmount(): Flow<Long?>{
        return cartDao.getTotalCartAmount().mapExceptionFlow()
            .flowOn(ioDispatcher)
    }

    override suspend fun addOrIncrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            cartDao.addOrIncrementProduct(productId)
        }
    }

    override suspend fun decrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            cartDao.decrementOrRemoveProduct(productId)
        }
    }

    override suspend fun clearCart() =
        withContext(ioDispatcher) {
            runMapExceptionSuspending {
                cartDao.deleteAllCart()
            }
        }
}