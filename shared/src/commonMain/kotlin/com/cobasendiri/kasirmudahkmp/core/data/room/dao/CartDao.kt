package com.cobasendiri.kasirmudahkmp.core.data.room.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import com.cobasendiri.kasirmudahkmp.core.data.room.entity.CartEntity
import com.cobasendiri.kasirmudahkmp.core.data.room.result.ProductResult
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("""
        SELECT p.*, c.count
        FROM products as p
        INNER JOIN carts as c ON p.id = c.product_id
    """)
    fun getAllCartProducts(): Flow<List<ProductResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(cart: CartEntity): Long

    @Query("UPDATE carts SET count = count + 1 WHERE product_id = :productId ")
    suspend fun incrementProduct(productId: String)

    @Transaction
    suspend fun addOrIncrementProduct(productId: String){
        val inserted = addToCart(CartEntity(productId, 1))
        if(inserted == -1L){
            incrementProduct(productId)
        }
    }

    @Query("UPDATE carts SET count = MAX(0, count - 1) WHERE product_id = :productId ")
    suspend fun decrementProduct(productId: String)

    @Query("DELETE FROM carts WHERE product_id = :productId AND count <= 0")
    suspend fun removeFromCartIfCountZero(productId: String)

    @Transaction
    suspend fun decrementOrRemoveProduct(productId: String){
        decrementProduct(productId)
        removeFromCartIfCountZero(productId)
    }

    @Query("""
        SELECT SUM (p.price * c.count)
        FROM products AS p
        INNER JOIN carts AS C ON p.id = c.product_id
    """)
    fun getTotalCartAmount(): Flow<Long?>

    @Query("DELETE FROM carts")
    suspend fun deleteAllCart()
}