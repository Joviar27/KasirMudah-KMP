package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetCartListUseCase(
    private val cartRepository: ICartRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetCartListUseCase")

    fun invoke(searchQuery: String): Flow<Result<List<ProductInfo>>> {
        return cartRepository.getAllCartProduct(searchQuery)
            .map {
                if(it==null){
                    logWarning("Cart list is null")
                    Result.Error(KasirMudahException.UnknownError(null))
                }else{
                    logInfo("Cart list: $it")
                    Result.Success(it)
                }
            }.catch {
                Result.Error(it)
            }
    }
}