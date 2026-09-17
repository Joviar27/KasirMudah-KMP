package com.cobasendiri.kasirmudahkmp.core.data.repository

import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudahkmp.core.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudahkmp.core.data.room.dao.CartDao
import com.cobasendiri.kasirmudahkmp.core.data.util.DataMapper.mapToDomain
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.model.TransactionItemInfo
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

    companion object{
        private const val LOGGER_TAG = "CartRepository"
    }

    override fun getAllCartProduct(searchQuery: String): Flow<List<ProductInfo>?>{
        val log = Pair(LOGGER_TAG, "getAllCartProduct")
        return cartDao.getAllCartProducts()
            .mapExceptionFlow(log){ products ->
                val filtered = if(searchQuery.isNotEmpty()){
                    products.filter { it.productEntity.name.contains(searchQuery, true) }
                }else {
                    products
                }
                filtered.mapListToDomain()
            }.flowOn(ioDispatcher)
    }

    override fun getTotalCartAmount(): Flow<Long?>{
        val log = Pair(LOGGER_TAG, "getTotalCartAmount")
        return cartDao.getTotalCartAmount().mapExceptionFlow(log)
            .flowOn(ioDispatcher)
    }

    override suspend fun addOrIncrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        val log = Pair(LOGGER_TAG, "addOrIncrementProduct")
        runMapExceptionSuspending(log) {
            cartDao.addOrIncrementProduct(productId)
        }
    }

    override suspend fun decrementProduct(
        productId: String
    ) = withContext(ioDispatcher) {
        val log = Pair(LOGGER_TAG, "decrementProduct")
        runMapExceptionSuspending(log) {
            cartDao.decrementOrRemoveProduct(productId)
        }
    }

    override suspend fun clearCart() =
        withContext(ioDispatcher) {
            val log = Pair(LOGGER_TAG, "clearCart")
            runMapExceptionSuspending(log) {
                cartDao.deleteAllCart()
            }
        }

    override suspend fun getProductsTotal(): List<TransactionItemInfo> {
        return withContext(ioDispatcher) {
            val log = Pair(LOGGER_TAG, "getProductsTotal")
            runMapExceptionSuspending(log) {
                cartDao.getProductsTotalAmount().mapToDomain()
            }
        }
    }
}